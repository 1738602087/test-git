/**
 *
 */
package cn.repeatlink.module.judicial.service.impl;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.hutool.core.util.StrUtil;
import cn.repeatlink.module.judicial.vo.estate.FaceVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.rekognition.model.FaceMatch;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.UserGeneral;
import cn.repeatlink.common.mapper.UserGeneralFaceMapper;
import cn.repeatlink.common.mapper.UserGeneralMapper;
import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.module.general.service.IGeneralUserService;
import cn.repeatlink.module.general.vo.user.UserInfo;
import cn.repeatlink.module.judicial.common.Constant.UserFaceStatus;
import cn.repeatlink.module.judicial.common.JudicialResultCode;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.service.IFaceService;
import cn.repeatlink.module.judicial.service.IUserFaceService;
import cn.repeatlink.module.judicial.util.FaceCodeStore;
import cn.repeatlink.module.judicial.vo.face.FaceCodeInfo;
import cn.repeatlink.module.judicial.vo.face.FaceInfo;
import cn.repeatlink.module.judicial.vo.user.FaceUserVo;
import lombok.extern.log4j.Log4j2;

/**
 * @author LAI
 * @date 2020-09-23 11:24
 *
 * 用户人脸识别服务类
 */

@Log4j2
@Service
public class UserFaceServiceImpl implements IUserFaceService {

    @Autowired
    private UserGeneralMapper userGeneralMapper;

    @Autowired
    private UserGeneralFaceMapper userGeneralFaceMapper;

    @Resource(name = "awsFaceService")
    private IFaceService faceService;

    @Autowired
    private IGeneralUserService generalUserService;

    @Override
    public void inputFaceData(String operaUserId, String userId, String picBase64Data) {
        if (StringUtils.isBlank(picBase64Data)) {
            throw JudicialRuntimeException.build("judicial.user.face.input.data.null");
        }

        UserGeneral user = this.checkUser(userId);

        // 检测分析人脸
        this.faceService.detectFace(picBase64Data);
        // 满足要求，先做成本地数据保存，再请求API，再更新本地数据（不需要事务）
        final Record record = new Record();
        record.set("id", IDUtil.nextID());
        record.set("user_general_id", userId);
        final String externalImageId = userId;
        record.set("external_image_id", externalImageId);
        record.set("face_data", picBase64Data.getBytes());
        record.set("status", Constant.STATUS_INVALID);
        record.set("created_by", operaUserId);
        record.set("create_time", new Date());
        if (!Db.save("user_general_face", record)) {
            //
            throw JudicialRuntimeException.build("judicial.user.face.input.save.fail");
        }

        // 调用API
        FaceInfo faceInfo = this.faceService.saveFaceData(externalImageId, picBase64Data);
        if (faceInfo == null || StringUtils.isBlank(faceInfo.getFaceId())) {
            throw JudicialRuntimeException.build("judicial.user.face.api.add.error");
        }

        // 更新本地数据
        record.set("face_id", faceInfo.getFaceId());
        record.set("collection_id", faceInfo.getCollectionId());
        record.set("status", Constant.STATUS_VALID);
        boolean flag = false;
        try {
            flag = Db.update("user_general_face", "id", record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!flag) {
            // 日志记录人脸ID
            log.error("face_id={0} save db fail. faceinfo={1}", faceInfo.getFaceId(), JSONUtil.toJsonStr(faceInfo));
            // 请求删除刚才添加的人脸
            List<String> deleteFaceData = this.faceService.deleteFaceData(faceInfo.getFaceId());
            if (deleteFaceData == null || deleteFaceData.isEmpty()) {
                log.error("face_id={0} delete fail.", faceInfo.getFaceId());
            }
            throw JudicialRuntimeException.build("judicial.user.face.input.save.fail");
        }

        // 既有的颜情报信息？删除？保留？TODO
        this.deleteOld(userId, faceInfo.getFaceId(), picBase64Data);
        Db.update("delete from user_general_face where user_general_id=? and id <> ?", userId, record.getStr("id"));
    }

    @Override
    public void deleteFaceByUserId(String operaUserId, String userId) {
        List<Record> list = Db.find("select * from user_general_face where user_general_id=? and `status`=?", userId, UserFaceStatus.VALID);
        if (list != null) {
            List<String> faceIds = new ArrayList<>();
            for (Record record : list) {
                String faceId = record.getStr("face_id");
                if (StringUtils.isNotBlank(faceId)) {
                    faceIds.add(faceId);
                }
            }
            this.deleteUserFace(operaUserId, userId, faceIds.toArray(new String[0]));
        }
    }

    public void deleteUserFace(String operaUserId, String userId, String... faceIds) {
        if (faceIds != null) {
            for (String faceId : faceIds) {
                Db.update("update user_general_face set `status`=?, updated_by=?, update_time=? where user_general_id=? and face_id in (?) "
                        , UserFaceStatus.REQ_DELETE, operaUserId, new Date(), userId, faceId);

                List<String> faceIdList = this.faceService.deleteFaceData(faceId);
                if (faceIdList != null && !faceIdList.isEmpty()) {
                    Db.update("update user_general_face set `status`=?, update_time=? where user_general_id=? and face_id in (?) "
                            , UserFaceStatus.DELETED, new Date(), userId, faceId);
                }
            }
        }
    }

    @Override
    public List<String> getUserFaceIds(String userId) {
        List<String> faceList = new ArrayList<>();
        List<Record> list = Db.find("select face_id from user_general_face where user_general_id=? and status=? ", userId, Constant.STATUS_VALID);
        if (list != null) {
            for (Record record : list) {
                String str = record.getStr("face_id");
                if (StringUtils.isNotBlank(str)) {
                    faceList.add(str);
                }
            }
        }
        return faceList;
    }

    public FaceVo getUserFaceInfo(String userId) {
        Record record = Db.findFirst("select face_id,create_time,update_time from user_general_face where user_general_id=? and status=? ", userId, Constant.STATUS_VALID);
        if (record == null)
            return null;
        if (StrUtil.isBlank(record.getStr("update_time")))
            return new FaceVo(record.getStr("face_id"), record.getDate("create_time"));
        return new FaceVo(record.getStr("face_id"), record.getDate("update_time"));
    }

    @Override
    public String getUserIdByImage(String base64data) {
        String faceId = this.faceService.searchFaceByImage(base64data);
        if (StringUtils.isNotBlank(faceId)) {
            return this.getUserId(faceId);
        }
        return null;
    }

    @Override
    public FaceUserVo getUserByImage(String base64data) {
        FaceUserVo user = null;
        String userId = this.getUserIdByImage(base64data);
        if (StringUtils.isNotBlank(userId)) {
            UserInfo userInfo = this.generalUserService.getUserInfo(userId);
            if (userInfo != null) {
                user = new FaceUserVo();
                user.setUser_id(userId);
                user.setAddr(StringUtils.trimToNull(StringUtils.trimToEmpty(userInfo.getAddr1()) + StringUtils.trimToEmpty(userInfo.getAddr2()) + StringUtils.trimToEmpty(userInfo.getAddr3())));
                user.setGender(userInfo.getGender());
                user.setBirthday(DateUtil.formatDate(userInfo.getBirthday()));
                user.setFullname(StringUtils.trimToNull(StringUtils.trimToEmpty(userInfo.getFamname()) + StringUtils.trimToEmpty(userInfo.getGivename())));
                user.setFullname_kana(StringUtils.trimToNull(StringUtils.trimToEmpty(userInfo.getFamname_kana()) + StringUtils.trimToEmpty(userInfo.getGivename_kana())));
            }
        }
        return user;
    }

    @Override
    public String getUserId(String faceId) {
        return StringUtils.trimToNull(Db.queryStr("select user_general_id from user_general_face where face_id = ? and `status` = 1 limit 1", faceId));
    }

    @Override
    public FaceCodeInfo buildFaceCode(FaceUserVo user) {
        String faceCode = IdUtil.fastSimpleUUID() + IdUtil.fastSimpleUUID();
        FaceCodeInfo info = user.getFace_code_info();
        info.setFace_code(faceCode);
        // 其他信息设定（token、ip等其他标识）

        // 缓存起来
        FaceCodeStore.put(faceCode, user);
        return info;
    }

    @Override
    public FaceUserVo verifyFaceCode(String userId, String use_type, FaceCodeInfo info) {
        if (info == null || StringUtils.isBlank(info.getFace_code())) {

        }
        FaceUserVo vo = FaceCodeStore.get(info.getFace_code());
        if (vo != null && StringUtils.isNotBlank(vo.getUser_id()) && vo.getUser_id().equals(userId) && vo.getFace_code_info() != null && use_type.equals(vo.getFace_code_info().getUse_type())) {
            // 有效数据
            // 其他信息验证
            return vo;
        }
        // 无效抛错
        throw JudicialRuntimeException.build(JudicialResultCode.FACE_CODE_INVALID);
    }


    private void deleteOld(String userId, String newFaceId, String image64data) {
        List<FaceMatch> list = this.faceService.searchFacesByImage(image64data);
        if (list != null) {
            List<String> faceIdList = new ArrayList<>();
            for (FaceMatch m : list) {
                if (m.getFace().getFaceId().equals(newFaceId)) {
                    continue;
                }
                if (1 == 1 || StringUtils.equals(m.getFace().getExternalImageId(), userId)) {
                    faceIdList.add(m.getFace().getFaceId());
                }
            }
            if (!faceIdList.isEmpty()) {
                this.deleteUserFace(userId, userId, faceIdList.toArray(new String[0]));
            }
        }
    }

    private UserGeneral checkUser(String userId) {
        UserGeneral userGeneral = this.userGeneralMapper.selectByPrimaryKey(userId);
        if (userGeneral == null || !Constant.STATUS_VALID.equals(userGeneral.getStatus())) {
            throw JudicialRuntimeException.build("judicial.user.not.found");
        }
        return userGeneral;
    }

}
