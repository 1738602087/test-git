package cn.repeatlink.module.law.scheduler.updatepostcode.imp;



public interface DownLoadFileI {
	
	public boolean  downLoad(String remoteFilePath,String localFile);
	
	public Long getRemoteFileLastUpdateTime(String remoteFilePath);
}
