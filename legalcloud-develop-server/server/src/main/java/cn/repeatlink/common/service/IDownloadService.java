package cn.repeatlink.common.service;

import java.io.File;
import java.io.InputStream;

public interface IDownloadService {

	/**
	 * @param is
	 * @return
	 */
	String saveDownloadFile(InputStream is);

	/**
	 * @param file
	 * @return
	 */
	String saveDownloadFile(File file);

	/**
	 * @return
	 */
	String getDownloadBasePath();
	
}
