package cn.repeatlink.common.mapper;

import java.util.List;

import cn.repeatlink.common.entity.SysConfig;
import cn.repeatlink.module.manage.dto.ConfigInfo;

public interface SysConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_config
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer configId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_config
     *
     * @mbg.generated
     */
    int insert(SysConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_config
     *
     * @mbg.generated
     */
    SysConfig selectByPrimaryKey(Integer configId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_config
     *
     * @mbg.generated
     */
    List<SysConfig> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_config
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysConfig record);
    
    List<ConfigInfo> search(ConfigInfo configInfo);
    
    List<SysConfig> selectByConfigKey(String configKey);
}