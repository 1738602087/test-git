package cn.repeatlink.module.law.scheduler.updatepostcode.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import cn.hutool.core.io.FileUtil;

public class DownLoadPostCode implements DownLoadFileI {

	@Override
	public boolean downLoad(String remoteFilePath, String localFile) {
		
		URL url = null;
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		FileOutputStream fos = null;
		try {
			url = new URL(remoteFilePath);
			
			connection = (HttpURLConnection)url.openConnection();
			if (connection == null) {
				return false;
			}
			connection.connect();
			// URL重定向问题
			int responseCode = connection.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
				String newLoca = connection.getHeaderField("Location");
				return downLoad(newLoca, localFile);
			}
			inputStream = connection.getInputStream();
			File file = new File(localFile);
			if(!file.exists()) {
				FileUtil.touch(file);
			}
			fos = new FileOutputStream(localFile);
			byte[] bytesin = new byte[1024 * 1024];
			int byteread = 0;
			int bytesum = 0;
			while ((byteread = inputStream.read(bytesin)) != -1) {
				fos.write(bytesin, 0, byteread);
				bytesum += byteread;
			}
			fos.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(fos!=null){
				try{
					
					
					fos.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if(inputStream!=null){
				try{
					inputStream.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	public Long getRemoteFileLastUpdateTime(String remoteFilePath){
		URL url = null;
		URLConnection connection = null;
		Long lastUpdate=null;
		try {
			url = new URL(remoteFilePath);
			connection = url.openConnection();
			if (connection != null){
				lastUpdate=connection.getLastModified();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return lastUpdate;	
				
	}

}
