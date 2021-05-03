package cn.repeatlink.module.law.scheduler.updatepostcode.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.repeatlink.module.law.scheduler.updatepostcode.dto.FilePostCode;
import cn.repeatlink.module.law.scheduler.updatepostcode.dto.PostCodeCompareResulst;

public class UpdatePostCodeDaoImpl implements UpdatePostCodeDao {
  private static final Log log = LogFactory.getLog(UpdatePostCodeDaoImpl.class);


  @Override
  public String getRemoteFilePath(String...strs) {
    String path = null;
    String key = "postcode.updatePostCodeRemoteFilePath";
    if(strs!=null&&strs.length>0){
    	String str = strs[0];
    	if(str!=null&&str.equals("jigyosyo")){
    		key = "postcode.updatePostCodeJigyosyoRemoteFilePath";
    	}
    }
    
    try {
    	path = Db.queryStr("select config_value from sys_config where config_key=?", key);;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return path;
  }

  @Override
  public boolean creatTmpTable(String tableName) {
    String sql =
        "create table if not exists " + tableName + " (postcode  varchar(10) NOT NULL , " + "addr1  varchar(500) NULL , " + "addr2  varchar(500) NULL , " + "addr3  varchar(500) NULL , " + "addr1hiragana  varchar(500) NULL , " + "addr2hiragana  varchar(500) NULL , "
            + "addr3hiragana  varchar(500) NULL , " + "PRIMARY KEY (postcode))";
    try {
    	Db.update(sql);
		return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean insertToTmptable(FilePostCode obj, String tableName) {
    try {
    	Record record = new Record();
    	record.set("postcode", obj.getPostCode());
    	record.set("addr1", obj.getAddr1());
    	record.set("addr2", obj.getAddr2());
    	record.set("addr3", obj.getAddr3());
    	record.set("addr1hiragana", obj.getAddr1Hiragana());
    	record.set("addr2hiragana", obj.getAddr2Hiragana());
    	record.set("addr3hiragana", obj.getAddr3Hiragana());
    	return Db.save(tableName, record);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean insertToTmptable(List<FilePostCode> objs, String tableName) {
    Set<String> set = new HashSet<String>();
    boolean result = true;
    String insertSql="insert into "+tableName+" (postcode,addr1,addr2,addr3,addr1hiragana,addr2hiragana,addr3hiragana)values ";
    StringBuilder sb=new StringBuilder(insertSql);
    List<Object> params = new ArrayList<Object>();
    int insertCount=0;
    if (objs != null && objs.size() > 0) {
      for (int index = 0; index < objs.size(); index++) {
        FilePostCode obj = objs.get(index);
         if(set.add(obj.getPostCode())){
           insertCount++;
           sb.append("(?,?,?,?,?,?,?),");
           params.add(obj.getPostCode());
           params.add(obj.getAddr1());
           params.add(obj.getAddr2());
           params.add(obj.getAddr3());
           params.add(obj.getAddr1Hiragana());
           params.add(obj.getAddr2Hiragana());
           params.add(obj.getAddr3Hiragana());
         }
        if(insertCount==1000){
          String sql=sb.substring(0,sb.length()-1);
          insertCount=0;
          sb=new StringBuilder(insertSql);
          try{
        	  Db.update(sql, params.toArray());
          }catch(Exception e){
              e.printStackTrace();
          }finally {
        	  params.clear();
		}
        }
         
      }
      if(insertCount!=0){
        String sql=sb.substring(0,sb.length()-1);
        insertCount=0;
        sb=new StringBuilder(insertSql);
        
        try{
        	Db.update(sql, params.toArray());
        }catch(Exception e){
          e.printStackTrace();
        }finally {
        	params.clear();
		}
      }
    }
    return result;
  }
  
  @Override
  public List<PostCodeCompareResulst> queryAddedPostCode(String tableName) {
    String sql =
        "select pt.postcode as postcode,pt.addr1 as addr1,pt.addr2 as addr2,pt.addr3 as addr3, " + " pt.addr1hiragana as addr1hiragana,pt.addr2hiragana as addr2hiragana,pt.addr3hiragana as addr3hiragana from " + tableName
            + " pt left join  postcode po on po.postcode=pt.postcode where po.postcode is null ";
    List<PostCodeCompareResulst> results = new ArrayList<PostCodeCompareResulst>();
    try {
      List<Record> queryResults = Db.find(sql);

      if (queryResults != null && queryResults.size() > 0) {
        for (Record record : queryResults) {
          PostCodeCompareResulst result = new PostCodeCompareResulst();
          result.setPostCode(record.getStr("postcode"));
          result.setNewAddr1(record.getStr("addr1"));
          result.setNewAddr2(record.getStr("addr2"));
          result.setNewAddr3(record.getStr("addr3"));
          result.setNewAddr1Hiragana(record.getStr("addr1hiragana"));
          result.setNewAddr2Hiragana(record.getStr("addr2hiragana"));
          result.setNewAddr3Hiragana(record.getStr("addr3hiragana"));
          results.add(result);

        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public List<PostCodeCompareResulst> queryUpdatePostCode(String tableName) {
    String sql =
        "select pt.postcode as postcode,pt.addr1 as addr1,pt.addr2 as addr2,pt.addr3 as addr3, " + " pt.addr1hiragana as addr1hiragana,pt.addr2hiragana as addr2hiragana,pt.addr3hiragana as addr3hiragana, "
            + "po.addr1 as oaddr1,po.addr2 as oaddr2,po.addr3 as oaddr3,po.addr3hiragana as oaddr3hiragana " + "from " + tableName + " pt  join  postcode po on po.postcode=pt.postcode where (po.addr1!=pt.addr1 or po.addr2!=pt.addr2 or po.addr3!=pt.addr3" + " or po.addr3hiragana!=pt.addr3hiragana ) ";
    List<PostCodeCompareResulst> results = new ArrayList<PostCodeCompareResulst>();
    try {
      List<Record> queryResults = Db.find(sql);
      if (queryResults != null && queryResults.size() > 0) {
        for (Record record : queryResults) {
          PostCodeCompareResulst result = new PostCodeCompareResulst();
          result.setPostCode(record.getStr("postcode"));
          result.setNewAddr1(record.getStr("addr1"));
          result.setNewAddr2(record.getStr("addr2"));
          result.setNewAddr3(record.getStr("addr3"));
          result.setNewAddr1Hiragana(record.getStr("addr1hiragana"));
          result.setNewAddr2Hiragana(record.getStr("addr2hiragana"));
          result.setNewAddr3Hiragana(record.getStr("addr3hiragana"));
          result.setOldAddr1(record.getStr("oaddr1"));
          result.setOldAddr2(record.getStr("oaddr2"));
          result.setOldAddr3(record.getStr("oaddr3"));
          result.setOldAddr3Hiragana(record.getStr("oaddr3hiragana"));
          results.add(result);

        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public boolean updateSystemPostCode(String tableName) {

    return updateOldPostcode(tableName) && insertNewPostcode(tableName);
  }

  private boolean insertNewPostcode(String tableName) {
    String sql="insert into postcode(postcode,addr1,addr2,addr3,addr3hiragana,multi) "+
    "select np.postcode,np.addr1,np.addr2,np.addr3,np.addr3hiragana,0 "+
    "from "+tableName+ " as np "+
    "left join postcode p on np.postcode=p.postcode "+
    "where p.postcode is null";
    try {
      Db.update(sql);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
    
  }

  private boolean updateOldPostcode(String tableName) {
    String sql = "update postcode p," + tableName + " np set p.addr1=np.addr1 ,p.addr2=np.addr2,p.addr3=np.addr3,p.addr3hiragana=np.addr3hiragana" + " where p.postcode=np.postcode and (p.addr1!=np.addr1 or p.addr2!=np.addr2 or p.addr3!=np.addr3  or p.addr3hiragana!=np.addr3hiragana)";
    try {
      Db.update(sql);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  @Override
  public List<PostCodeCompareResulst> queryAddedPostCodeJigyosyo(String tableName) {
    String sql =
        "select pt.postcode as postcode,pt.addr1 as addr1,pt.addr2 as addr2,pt.addr3 as addr3, " 
        	+ " pt.addr1hiragana as addr4,pt.addr2hiragana as office,pt.addr3hiragana as officehiragana from " 
        	+ tableName
            + " pt left join  postcode_jigyosyo po on po.postcode=pt.postcode where po.postcode is null ";
    List<PostCodeCompareResulst> results = new ArrayList<PostCodeCompareResulst>();
    try {
      List<Record> queryResults = Db.find(sql);

      if (queryResults != null && queryResults.size() > 0) {
        for (Record record : queryResults) {
          PostCodeCompareResulst result = new PostCodeCompareResulst();
          result.setPostCode(record.getStr("postcode"));
          result.setNewAddr1(record.getStr("addr1"));
          result.setNewAddr2(record.getStr("addr2"));
          result.setNewAddr3(record.getStr("addr3"));
          result.setNewAddr1Hiragana(record.getStr("addr4"));
          result.setNewAddr2Hiragana(record.getStr("office"));
          result.setNewAddr3Hiragana(record.getStr("officehiragana"));
          results.add(result);

        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public List<PostCodeCompareResulst> queryUpdatePostCodeJigyosyo(String tableName) {
    String sql =
        "select pt.postcode as postcode,pt.addr1 as addr1,pt.addr2 as addr2,pt.addr3 as addr3, " 
        	+ " pt.addr1hiragana as addr4,pt.addr2hiragana as office,pt.addr3hiragana as officehiragana, "
            + "po.addr1 as oaddr1,po.addr2 as oaddr2,po.addr3 as oaddr3,po.addr4 as oaddr4, " 
        	+ " po.office as ooffice,po.officehiragana as oofficehiragana "
            + " from " + tableName + " pt  join  postcode_jigyosyo po on po.postcode=pt.postcode where (po.addr1!=pt.addr1 or po.addr2!=pt.addr2 or po.addr3!=pt.addr3" 
        	+ " or po.addr4!=pt.addr1hiragana or po.office!=pt.addr2hiragana "
            + " or po.officehiragana!=pt.addr3hiragana ) ";
    List<PostCodeCompareResulst> results = new ArrayList<PostCodeCompareResulst>();
    try {
      List<Record> queryResults = Db.find(sql);
      if (queryResults != null && queryResults.size() > 0) {
        for (Record record : queryResults) {
          PostCodeCompareResulst result = new PostCodeCompareResulst();
          result.setPostCode(record.getStr("postcode"));
          result.setNewAddr1(record.getStr("addr1"));
          result.setNewAddr2(record.getStr("addr2"));
          result.setNewAddr3(record.getStr("addr3"));
          result.setNewAddr1Hiragana(record.getStr("addr4"));
          result.setNewAddr2Hiragana(record.getStr("office"));
          result.setNewAddr3Hiragana(record.getStr("officehiragana"));
          result.setOldAddr1(record.getStr("oaddr1"));
          result.setOldAddr2(record.getStr("oaddr2"));
          result.setOldAddr3(record.getStr("oaddr3"));
          result.setOldAddr1Hiragana(record.getStr("oaddr4"));
          result.setOldAddr2Hiragana(record.getStr("ooffice"));
          result.setOldAddr3Hiragana(record.getStr("oofficehiragana"));
          results.add(result);

        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public boolean updateSystemPostCodeJigyosyo(String tableName) {
	  return updateOldPostcodeJigyosyo(tableName) && insertNewPostcodeJigyosyo(tableName);
  }

  private boolean insertNewPostcodeJigyosyo(String tableName) {
    String sql="insert into postcode_jigyosyo(postcode,addr1,addr2,addr3,addr4,office,officehiragana,multi) "+
    "select np.postcode,np.addr1,np.addr2,np.addr3,np.addr1hiragana,np.addr2hiragana,np.addr3hiragana,0 "+
    "from "+tableName+ " as np "+
    "left join postcode_jigyosyo p on np.postcode=p.postcode "+
    "where p.postcode is null";
    try {
      Db.update(sql);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
    
  }

  private boolean updateOldPostcodeJigyosyo(String tableName) {
    String sql = "update postcode_jigyosyo p," + tableName + " np set p.addr1=np.addr1 ,p.addr2=np.addr2,p.addr3=np.addr3,p.addr4=np.addr1hiragana"
    		+ ",p.office=np.addr2hiragana,p.officehiragana=np.addr3hiragana "
    		+ " where p.postcode=np.postcode and (p.addr1!=np.addr1 or p.addr2!=np.addr2 or p.addr3!=np.addr3  or p.addr4!=np.addr1hiragana "
    		+ " or p.office!=np.addr2hiragana or p.officehiragana!=addr3hiragana)";
    try {
      Db.update(sql);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean dropTmpTable(String tableName) {
    String sql = "drop table if exists " + tableName;
    try {
    	Db.update(sql);
    	return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

}
