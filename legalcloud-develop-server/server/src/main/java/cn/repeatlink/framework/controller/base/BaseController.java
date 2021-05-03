package cn.repeatlink.framework.controller.base;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.io.FileUtil;
import cn.repeatlink.framework.util.SecurityUtils;
import cn.repeatlink.framework.util.ServletUtils;

public class BaseController {
	
	protected Long loginUserId() {
		return SecurityUtils.getLoginUser().getUserId();
	}
	
	protected void prepareDownloadFile(HttpServletResponse response, String fileName) {
		// response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + ServletUtils.getDownloadFileName(fileName));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
	}
	
	protected void downloadFile(HttpServletResponse response, File file, String fileName) {
		this.downloadFile(response, FileUtil.getInputStream(file), fileName);
	}
	protected void downloadFile(HttpServletResponse response, InputStream is, String fileName) {
		this.prepareDownloadFile(response, fileName);
        // 实现文件下载
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        try {
            if(is instanceof BufferedInputStream) {
            	bis = (BufferedInputStream)is;
            }
            else {
            	bis = new BufferedInputStream(is);
            }
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

}
