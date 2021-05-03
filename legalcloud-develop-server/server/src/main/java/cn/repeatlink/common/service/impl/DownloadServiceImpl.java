/**
 * 
 */
package cn.repeatlink.common.service.impl;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.repeatlink.common.service.IDownloadService;

/**
 * @author LAI
 * @date 2020-08-25 14:53
 * @TODO 下载服务类
 */

@Service
public class DownloadServiceImpl implements IDownloadService {
	
	@Autowired
	private Environment env;
	
	@Override
	public String saveDownloadFile(InputStream is) {
		// 得到流的MD5值
		String md5 = SecureUtil.md5(is);
		// 以MD5作为文件名保存文件至下载仓库，以可做断点下载
		String filePath = FileUtil.normalize(this.getDownloadBasePath() + "/" + md5);
		FileUtil.writeFromStream(is, filePath);
		return md5;
	}
	
	@Override
	public String saveDownloadFile(File file) {
		// 得到流的MD5值
		String md5 = SecureUtil.md5(file);
		// 以MD5作为文件名保存文件至下载仓库，以可做断点下载
		String filePath = FileUtil.normalize(this.getDownloadBasePath() + "/" + md5);
		FileUtil.copy(file, new File(filePath), false);
		return md5;
	}
	
	public void getFileSlice(String md5, long startIndex, int length) {
		
	}
	
	/**
	 * @doc 得到下载文件路径
	 * @return
	 */
	@Override
	public String getDownloadBasePath() {
		// 自定义下载文件路径
		String path = env.getProperty("rlframework.download.base-path");
		if(StringUtils.isNotBlank(path)) {
			return FileUtil.normalize(path);
		}
		// 默认下载文件路径
		path = System.getProperty("user.home");
		return FileUtil.normalize(path + "/rlframework/downloads");
	}

}
