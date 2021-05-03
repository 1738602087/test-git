/**
 *
 */
package cn.repeatlink.module.judicial.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import cn.repeatlink.module.judicial.util.DoubleEncrypt;
import org.springframework.stereotype.Service;

import com.jfinal.kit.Kv;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.template.engine.enjoy.EnjoyEngine;
import cn.repeatlink.framework.util.SysConfigCacheUtil;
import cn.repeatlink.module.judicial.common.Define;
import cn.repeatlink.module.judicial.service.IUserFaceImgService;
import lombok.extern.log4j.Log4j2;

/**
 *  人脸验证图片服务
 * @author LAI
 * @date 2020-12-01 13:56
 */

@Log4j2
@Service
public class UserFaceImgServiceImpl implements IUserFaceImgService {

    private static final String IMG_PATH_TEMPLATE = "/#date(time,'yyyy')/#date(time,'MM')/#date(time,'dd')/#date(time,'HH')/case_#(caseId)/user_#(userId).jpg";

    private static final EnjoyEngine ENJOY_ENGINE = new EnjoyEngine();

    @Override
    public void store(String caseId, String userId, String imgbase64data) {

        //进行AES+RSA双重加密
        String encryptImgDate = DoubleEncrypt.initialize().encrypt(imgbase64data);

        FileWriter writer = null;
        try {
            final String basePath = getBasePath();
            Kv kv = Kv.create();
            kv.set("time", new Date()).set("caseId", caseId).set("userId", userId);
            final String destPath = FileUtil.normalize(basePath + ENJOY_ENGINE.getTemplate(IMG_PATH_TEMPLATE).render(kv));
            FileUtil.touch(destPath);
            writer = new FileWriter(destPath);
            writer.write(encryptImgDate);
        } catch (Exception e) {
            log.error("人脸验证图片储存失败。案件ID：" + caseId + "，用户ID：" + userId, e);
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getBasePath() {
        return SysConfigCacheUtil.instance().getValue(Define.ConfigKeys.FACE_VERIFY_IMG_STORE_PATH);
    }

}
