/**
 * 
 */
package cn.repeatlink.module.judicial.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.EstateInfo;
import cn.repeatlink.common.entity.EstateUser;
import cn.repeatlink.common.mapper.EstateInfoMapper;
import cn.repeatlink.common.mapper.EstateUserMapper;
import cn.repeatlink.framework.cache.CacheKit;
import cn.repeatlink.framework.exception.BaseResultCode;
import cn.repeatlink.framework.util.IDUtil;
import cn.repeatlink.module.judicial.common.Define;
import cn.repeatlink.module.judicial.common.Define.TempCacheKeys;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.mapper.EstateMapper;
import cn.repeatlink.module.judicial.service.IEstateService;
import cn.repeatlink.module.judicial.service.IProtectService;
import cn.repeatlink.module.judicial.service.IUserFaceService;
import cn.repeatlink.module.judicial.vo.estate.EstateBaseVo;
import cn.repeatlink.module.judicial.vo.estate.EstateUserInfo;

/**
 * 房产相关服务类
 * @author LAI
 * @date 2020-09-24 10:24
 */

@Service
public class EstateServiceImpl implements IEstateService {
	
	@Autowired
	private EstateInfoMapper estateInfoMapper;
	
	@Autowired
	private EstateUserMapper estateUserMapper;
	
	@Autowired
	private EstateMapper estateMapper;
	
	@Autowired
	private IUserFaceService userFaceService;
	
	@Autowired
	private IProtectService protectService;
	
	@Override
	public List<EstateUserInfo> getEstateInfoListByUserFaceId(String faceId) {
		String userId = this.userFaceService.getUserId(faceId);
		if(StringUtils.isBlank(userId)) {
			
		}
		// 根据用户ID拿到名下房产列表
		List<EstateUserInfo> infoList = this.getEstateInfoListByUserId(userId);
		// 加上人脸识别标识，用于案件选择房产回传标识此用户已通过颜情报验证（使用缓存机制）
		if(infoList != null) {
			Map<String, String> faceCodeMap = CacheKit.getVal(Define.CACHE_NAME_TEMP, TempCacheKeys.USER_FACE_2_CODE);
			if(faceCodeMap == null) {
				faceCodeMap = new HashedMap<>();
				CacheKit.put(Define.CACHE_NAME_TEMP, TempCacheKeys.USER_FACE_2_CODE, faceCodeMap);
			}
			String faceCode = IdUtil.simpleUUID();
			for (EstateUserInfo info : infoList) {
				info.setFace_code(faceCode);
			}
			faceCodeMap.put(userId, faceCode);
		}
		return infoList;
	}
	
	@Override
	public List<EstateUserInfo> getEstateInfoListByUserId(String userId) {
		List<EstateUserInfo> infoList = this.estateMapper.findEstateListByUserId(userId);
		this.setEstateUserInfo(infoList);
		return infoList;
	}
	
	@Override
	public EstateUserInfo getEstateInfo(String estateId) {
		List<EstateUserInfo> infoList = new ArrayList<>();
		EstateInfo estate = this.estateInfoMapper.selectByPrimaryKey(estateId);
		if(estate == null) {
			throw JudicialRuntimeException.build("judicial.case.choose.estate.error.estate.not.found");
		}
		EstateUserInfo estateUserInfo = new EstateUserInfo();
		estateUserInfo.setEstate_id(estate.getEstateId());
		estateUserInfo.setHouse_id(estate.getHouseId());
		estateUserInfo.setAddr(estate.getAddr());
		estateUserInfo.setAddr_code(estate.getAddrCode());
		estateUserInfo.setAddr2(estate.getAddr2());
		infoList.add(estateUserInfo);
		// 获取房产名义人信息
		this.setEstateUserInfo(infoList);
		return infoList.get(0);
	}
	
	@Override
	public List<EstateUserInfo> getEstateInfoList(EstateUserInfo info) {
		if(info == null || StringUtils.isBlank(info.getAddr())) return null;
		// 统一使用全角字符
		if(info.getAddr() != null) {
			info.setAddr(Convert.toSBC(info.getAddr().trim()));
		}
		if(info.getAddr2() != null) {
			info.setAddr2(Convert.toSBC(info.getAddr2().trim()));
		}
		if(info.getHouse_id() != null) {
			info.setHouse_id(Convert.toSBC(info.getHouse_id().trim()));
		}
		List<EstateUserInfo> infoList = this.estateMapper.findEstateList(info);
		this.setEstateUserInfo(infoList);
		return infoList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void unbindOwner(String operUserId, String estateId) {
		// 异步安全问题？？？TODO
		List<EstateUser> list = this.estateMapper.getEstateUserByEstateId(estateId);
		if (list == null || list.isEmpty())
			return;
		// 物理删除关系记录
		for (EstateUser estateUser : list) {
			if (this.estateUserMapper.deleteByPrimaryKey(estateUser.getId()) <= 0) {
				throw JudicialRuntimeException.build("judicial.estate.error.unbind.owner.save.fail");
			}
			// 2021-02-07
			// 保护计划终止
			this.protectService.terminate(estateUser.getUserGeneralId(), estateId, "物件の紐付け関係解除");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void bindOwner(String operUserId, String estateId, List<String> userIdList) {
		if(userIdList == null || userIdList.isEmpty())
			return;
		// 异步安全问题？？？TODO
		// 必须先解除关系，再绑定关系（不允许直接替换）
		List<EstateUser> list = this.estateMapper.getEstateUserByEstateId(estateId);
		if(list != null && list.size() > 0) {
			throw JudicialRuntimeException.build("judicial.estate.error.bind.owner.exist");
		}
		// 新建记录保存
		for(String userId : userIdList) {
			EstateUser eu = new EstateUser();
			eu.setId(IDUtil.nextID());
			eu.setEstateId(estateId);
			eu.setUserGeneralId(userId);
			eu.setCreateTime(new Date());
			eu.setStatus(Constant.STATUS_VALID);
			if(this.estateUserMapper.insert(eu) <= 0) {
				throw JudicialRuntimeException.build("judicial.estate.error.bind.save.fail");
			}
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public EstateInfo createEstate(String operaUserId, EstateBaseVo vo) {
		if(StringUtils.isNotBlank(vo.getEstate_id())) return null;
		// 半角转全角
        if (StringUtils.isNotBlank(vo.getHouse_id())) {
            vo.setHouse_id(Convert.toSBC(vo.getHouse_id()));
        }
        if (StringUtils.isNotBlank(vo.getAddr())) {
        	vo.setAddr(Convert.toSBC(vo.getAddr()));
        }
        if (StringUtils.isNotBlank(vo.getAddr2())) {
        	vo.setAddr2(Convert.toSBC(vo.getAddr2()));
        }
		EstateInfo info = new EstateInfo();
		info.setEstateId(IDUtil.nextID());
		info.setHouseId(vo.getHouse_id());
		info.setAddr(vo.getAddr());
		info.setAddrCode(vo.getAddr_code());
		if(StringUtils.isBlank(info.getAddrCode())) {
			info.setAddrCode(info.getAddr());
		}
		// 2021-03-02
		// 添加字段所在地2
		info.setAddr2(vo.getAddr2());
		info.setArea(vo.getArea());
		info.setStruct(vo.getStruct());
		info.setType(vo.getType());
		info.setRecord(vo.getRecord());
		info.setStatus(Constant.STATUS_VALID);
		info.setCreatedBy(operaUserId);
		info.setCreateTime(new Date());
		
		if(this.estateInfoMapper.insert(info) <= 0) {
			throw JudicialRuntimeException.build(BaseResultCode.DB_SAVE_FAIL);
		}
		
		return info;
	}
	
	@Override
	public File getEstatePdfFile(String estateId) {
		EstateInfo info = this.estateInfoMapper.selectByPrimaryKey(estateId);
		String pdfPath = info.getPdfPath();
		File file = null;
		if(StringUtils.isNotBlank(pdfPath)) {
			file = new File(pdfPath);
			if(!file.exists() || !file.isFile()) {
				file = null;
			}
		}
		return file;
	}
	
	private void setEstateUserInfo(List<EstateUserInfo> infoList) {
		if(infoList != null) {
			for (EstateUserInfo info : infoList) {
				info.setUsers(this.estateMapper.findEstateUserListByEstateId(info.getEstate_id()));
			}
		}
	}

}
