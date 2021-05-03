package cn.repeatlink.common.mapper;

import cn.repeatlink.common.entity.EstateInfo;
import java.util.List;

public interface EstateInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String estateId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_info
     *
     * @mbg.generated
     */
    int insert(EstateInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_info
     *
     * @mbg.generated
     */
    EstateInfo selectByPrimaryKey(String estateId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_info
     *
     * @mbg.generated
     */
    List<EstateInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(EstateInfo record);
}