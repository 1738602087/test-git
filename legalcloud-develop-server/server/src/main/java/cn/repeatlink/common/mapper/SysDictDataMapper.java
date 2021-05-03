package cn.repeatlink.common.mapper;

import java.util.List;

import cn.repeatlink.common.entity.SysDictData;
import cn.repeatlink.module.manage.dto.DictDataInfo;

public interface SysDictDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_dict_data
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long dictCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_dict_data
     *
     * @mbg.generated
     */
    int insert(SysDictData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_dict_data
     *
     * @mbg.generated
     */
    SysDictData selectByPrimaryKey(Long dictCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_dict_data
     *
     * @mbg.generated
     */
    List<SysDictData> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_dict_data
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysDictData record);
    
    List<DictDataInfo> search(DictDataInfo dictInfo);
}