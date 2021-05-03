package cn.repeatlink.module.law.scheduler.updatepostcode;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;

import cn.repeatlink.common.service.IDownloadService;
import cn.repeatlink.framework.util.SpringBeanFactory;
import cn.repeatlink.module.law.scheduler.updatepostcode.dto.FilePostCode;
import cn.repeatlink.module.law.scheduler.updatepostcode.dto.PostCodeCompareResulst;
import cn.repeatlink.module.law.scheduler.updatepostcode.imp.DownLoadFileI;
import cn.repeatlink.module.law.scheduler.updatepostcode.imp.DownLoadPostCode;
import cn.repeatlink.module.law.scheduler.updatepostcode.imp.UpdatePostCodeDao;
import cn.repeatlink.module.law.scheduler.updatepostcode.imp.UpdatePostCodeDaoImpl;

public class UpdatePostCodePeriod {

  private DownLoadFileI downLoadFile=new DownLoadPostCode();
  private UpdatePostCodeDao updao =new UpdatePostCodeDaoImpl();                                                                                                                                      

  private int updateCount = 0;
  private int addCount = 0;
  private Long lastUpdateTime = 0L;
  private Long lastJigyosyoUpdateTime = 0L;
  private static final String POSTCODEUPDATERESULTFILEPATH = "/postCodeUpdate/";
  private static final String RESULTFILE = "updateNote.txt";
  private static final String RESULTADDDETAILFILE = "postAdd";
  private static final String RESULTUPDATEDETAILFILE = "postUpdate";
  private String today = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
  private String type = null;
  private static final String KEN_ALL = "ken_all",JIGYOSYO = "jigyosyo";
  private static boolean updating = false;
  public boolean excute() {
	  if(updating){
		  System.out.println("上一次batch还在执行。。。");
		  return false;
	  }
	updating = true;
	Map<String,String> paths = new HashMap<String, String>();
    String remoteFilePath = updao.getRemoteFilePath();
    if(remoteFilePath!=null&&!StringUtils.isBlank(remoteFilePath)){
    	paths.put(KEN_ALL,remoteFilePath.trim());
    }
    remoteFilePath = updao.getRemoteFilePath("jigyosyo");
    if(remoteFilePath!=null&&!StringUtils.isBlank(remoteFilePath)){
    	paths.put(JIGYOSYO,remoteFilePath.trim());
    }
    if (paths.size() == 0) {
    	updating = false;
      return false;
    }
    
    for(String key : paths.keySet()){
    	String path = paths.get(key);
    	type = key;
	    Long remoteFileLastUpdate = downLoadFile.getRemoteFileLastUpdateTime(path);
	    if (remoteFileLastUpdate == null) {
	      continue;
	    }else{
	    	if(type.equals(KEN_ALL))
	    		this.lastUpdateTime = remoteFileLastUpdate;
	    	else if(type.equals(JIGYOSYO))
	    		this.lastJigyosyoUpdateTime = remoteFileLastUpdate;
	    }
	    Long systemLastUpdate = queryLastUpdateTime();
	    if (systemLastUpdate == null || remoteFileLastUpdate > systemLastUpdate) {
	      String loalPath = this.getBasePath();
	      if (loalPath == null || "".equals(loalPath)) {
	        loalPath = this.getClass().getClassLoader().getResource("\\\\").toString();
	      }
	      if (!loalPath.endsWith(File.separator)) loalPath = loalPath + File.separator;
	
	      String localFile = loalPath + "postCode" + today +"_"+type+ ".zip";
	      if (downLoadFile.downLoad(path, localFile)) {
	        if (processLocalFile(localFile)) {
	        //  this.lastUpdateTime = remoteFileLastUpdate;
	        }
	      }
	
	    }
	}
    updating = false;
    return true;
  }

  public  boolean processLocalFile(String fileName) {

    try {
      List<FilePostCode> newPostCodes = readzipFile(fileName);
      if (newPostCodes == null || newPostCodes.size() == 0) {
        return true;
      }
      updatePostCode(newPostCodes);

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private List<FilePostCode> readzipFile(String fileName) throws Exception {
    ZipFile zf = new ZipFile(fileName);
    InputStream in = new BufferedInputStream(new FileInputStream(fileName));
    ZipInputStream zin = new ZipInputStream(in);
    ZipEntry ze;
    StrTokenizer st = new StrTokenizer();
    st.setIgnoreEmptyTokens(false);
    st.setDelimiterString(",");
    st.setIgnoredChar('"');
    List<FilePostCode> result = new ArrayList<FilePostCode>();
    FilePostCode fpc = new FilePostCode();
    int linecc=0;
    while ((ze = zin.getNextEntry()) != null) {
      if (ze.isDirectory()) {} else {
        
        long size = ze.getSize();
        if (size > 0) {
          BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze),"windows-31j"));
          String line;
          while ((line = br.readLine()) != null) {
            st.reset(line);
            String[] items = st.getTokenArray();
            if (items.length < 13) {
              continue;
            }
            linecc++;
            FilePostCode tmpCode = fpc.clone();
            if(type.equals(KEN_ALL)){
	            tmpCode.setPostCode(items[2]);
	            tmpCode.setAddr1(items[6]);
	            tmpCode.setAddr2(items[7]);
	            tmpCode.setAddr3(items[8]);
	            tmpCode.setAddr1Hiragana(items[3]);
	            tmpCode.setAddr2Hiragana(items[4]);
	            tmpCode.setAddr3Hiragana(items[5]);
            }else if(type.equals(JIGYOSYO)){
            	tmpCode.setPostCode(items[7]);
	            tmpCode.setAddr1(items[3]);
	            tmpCode.setAddr2(items[4]);
	            tmpCode.setAddr3(items[5]);
	            tmpCode.setAddr1Hiragana(items[6]);
	            tmpCode.setAddr2Hiragana(items[2]);
	            tmpCode.setAddr3Hiragana(items[1]);
            }else{
            	break;
            }
            result.add(tmpCode);
          }
          br.close();
        }
      }
      zin.closeEntry();
    }
    System.out.println(linecc);
    return result;
  }

  private boolean updatePostCode(List<FilePostCode> newPostCodes) {

    String tmpTable = "tmp_updatePostCode_" + today;
    boolean flag = true;
    try {
      flag = this.updao.creatTmpTable(tmpTable);

      flag = flag && this.updao.insertToTmptable(newPostCodes, tmpTable);
      if (flag) {
        List<PostCodeCompareResulst> tmpps = null;
        if(type.equals(KEN_ALL))
        	tmpps = this.updao.queryAddedPostCode(tmpTable);
        else if(type.equals(JIGYOSYO))
        	tmpps = this.updao.queryAddedPostCodeJigyosyo(tmpTable);
        if (tmpps != null) {
          this.addCount += tmpps.size();
        }
        List<PostCodeCompareResulst> tmpiupdateps = null;
        if(type.equals(KEN_ALL))
        	tmpiupdateps = this.updao.queryUpdatePostCode(tmpTable);
        else if(type.equals(JIGYOSYO))
        	tmpiupdateps = this.updao.queryUpdatePostCodeJigyosyo(tmpTable);
        if (tmpiupdateps != null) {
          this.updateCount += tmpiupdateps.size();
        }
        saveNewPostCodeInfo(tmpps);
        saveUpdateedPostCodeInfo(tmpiupdateps);
      }
      if(type.equals(KEN_ALL))
    	  flag = flag && this.updao.updateSystemPostCode(tmpTable);
      else if(type.equals(JIGYOSYO))
    	  flag = flag && this.updao.updateSystemPostCodeJigyosyo(tmpTable);
      if (flag) saveUpdateInfo2File();
    } catch (Exception e) {
      flag = false;
    } finally {
      this.updao.dropTmpTable(tmpTable);
    }
    return flag;
  }

  private Long queryLastUpdateTime() {
    String loalPath = this.getBasePath();
    String filePath = loalPath + POSTCODEUPDATERESULTFILEPATH;
    Long lastUpdateTime = null;
    File resultFile = new File(filePath + RESULTFILE);
    if (!resultFile.exists()) {
      return lastUpdateTime;
    }
    RandomAccessFile randacees = null;
    try {
      randacees = new RandomAccessFile(resultFile, "r");
      randacees.seek(0);
      String value = randacees.readUTF();
      String items[] = value.split(",");
      if (items.length > 3) {
    	  if(type.equals(KEN_ALL))
    		  lastUpdateTime = Long.parseLong(items[1], 10);
    	  else if(type.equals(JIGYOSYO))
    		  lastUpdateTime = Long.parseLong(items[2], 10);
        
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (randacees != null) randacees.close();
      } catch (Exception e) {

      }
    }

    return lastUpdateTime;

  }

  private void saveUpdateInfo2File() {
    String loalPath = this.getBasePath();
    String filePath = loalPath + POSTCODEUPDATERESULTFILEPATH;
    File resultFile = new File(filePath);
    if (!resultFile.exists()) {
      resultFile.mkdirs();
    }
    resultFile = new File(filePath + RESULTFILE);
    RandomAccessFile randacees = null;
    try {
      randacees = new RandomAccessFile(resultFile, "rw");
      randacees.seek(0);
      String result = this.today + "," + this.lastUpdateTime + "," + this.lastJigyosyoUpdateTime + "," + this.addCount + "," + this.updateCount + "\r\n";
      randacees.writeUTF(result);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (randacees != null) randacees.close();
      } catch (Exception e) {

      }
    }

  }

  private void saveNewPostCodeInfo(List<PostCodeCompareResulst> objs) {
    String loalPath = this.getBasePath();
    String filePath = loalPath + POSTCODEUPDATERESULTFILEPATH;
    File resultFile = new File(filePath);
    if (!resultFile.exists()) {
      resultFile.mkdirs();
    }
    String fileName = filePath + RESULTADDDETAILFILE +"_"+type+ "." + today;
    FileOutputStream fos = null;
    try {
      StringBuilder sb = new StringBuilder();
      fos = new FileOutputStream(fileName);
      if (objs != null) {
        for (PostCodeCompareResulst obj : objs) {
          sb.setLength(0);
          sb.append(obj.getPostCode()).append(",");
          sb.append(obj.getNewAddr1()).append(",");
          sb.append(obj.getNewAddr2()).append(",");
          sb.append(obj.getNewAddr3()).append(",");
          sb.append(obj.getNewAddr1Hiragana()).append(",");
          sb.append(obj.getNewAddr2Hiragana()).append(",");
          sb.append(obj.getNewAddr3Hiragana()).append("\r\n");
          fos.write(sb.toString().getBytes("UTF-8"));
        }
      }

    } catch (Exception e) {

    } finally {
      if (fos != null) {
        try {
          fos.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

  }

  private void saveUpdateedPostCodeInfo(List<PostCodeCompareResulst> objs) {
    String loalPath = this.getBasePath();
    String filePath = loalPath + POSTCODEUPDATERESULTFILEPATH;
    File resultFile = new File(filePath);
    if (!resultFile.exists()) {
      resultFile.mkdirs();
    }
    String fileName = filePath + RESULTUPDATEDETAILFILE +"_"+type+ "." + today;
    FileOutputStream fos = null;
    try {
      StringBuilder sb = new StringBuilder();
      fos = new FileOutputStream(fileName);
      if (objs != null) {
        for (PostCodeCompareResulst obj : objs) {
          sb.setLength(0);
          sb.append(obj.getPostCode()).append(",");
          sb.append(obj.getOldAddr1()).append(",");
          sb.append(obj.getOldAddr2()).append(",");
          sb.append(obj.getOldAddr3()).append(",");
          sb.append(obj.getOldAddr1Hiragana()).append(",");
          sb.append(obj.getOldAddr2Hiragana()).append(",");
          sb.append(obj.getOldAddr3Hiragana()).append(",");
          sb.append(obj.getNewAddr1()).append(",");
          sb.append(obj.getNewAddr2()).append(",");
          sb.append(obj.getNewAddr3()).append(",");
          sb.append(obj.getNewAddr1Hiragana()).append(",");
          sb.append(obj.getNewAddr2Hiragana()).append(",");
          sb.append(obj.getNewAddr3Hiragana()).append("\r\n");
          fos.write(sb.toString().getBytes("UTF-8"));
        }
      }

    } catch (Exception e) {

    } finally {
      if (fos != null) {
        try {
          fos.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  public DownLoadFileI getDownLoadFile() {
    return downLoadFile;
  }

  public void setDownLoadFile(DownLoadFileI downLoadFile) {
    this.downLoadFile = downLoadFile;
  }

  public UpdatePostCodeDao getUpdao() {
    return updao;
  }

  public void setUpdao(UpdatePostCodeDao updao) {
    this.updao = updao;
  }
  
  private String getBasePath() {
	  IDownloadService downloadService = SpringBeanFactory.getBean(IDownloadService.class);
	  return downloadService.getDownloadBasePath();
  }

}
