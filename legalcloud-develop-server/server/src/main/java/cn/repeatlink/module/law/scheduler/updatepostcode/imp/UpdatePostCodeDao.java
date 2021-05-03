package cn.repeatlink.module.law.scheduler.updatepostcode.imp;

import java.util.List;

import cn.repeatlink.module.law.scheduler.updatepostcode.dto.FilePostCode;
import cn.repeatlink.module.law.scheduler.updatepostcode.dto.PostCodeCompareResulst;

public interface UpdatePostCodeDao {

  public String getRemoteFilePath(String...strs);

  public boolean creatTmpTable(String tableName);

  public boolean insertToTmptable(FilePostCode obj, String tableName);

  public boolean insertToTmptable(List<FilePostCode> objs, String tableName);

  public List<PostCodeCompareResulst> queryAddedPostCode(String tableName);

  public List<PostCodeCompareResulst> queryUpdatePostCode(String tableName);

  public boolean updateSystemPostCode(String tableName);

  public boolean dropTmpTable(String tableName);

boolean updateSystemPostCodeJigyosyo(String tableName);

List<PostCodeCompareResulst> queryUpdatePostCodeJigyosyo(String tableName);

List<PostCodeCompareResulst> queryAddedPostCodeJigyosyo(String tableName);

}
