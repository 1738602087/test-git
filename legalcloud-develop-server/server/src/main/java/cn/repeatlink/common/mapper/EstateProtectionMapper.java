package cn.repeatlink.common.mapper;

import cn.repeatlink.common.entity.EstateProtection;
import java.util.List;

public interface EstateProtectionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_protection
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_protection
     *
     * @mbg.generated
     */
    int insert(EstateProtection record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_protection
     *
     * @mbg.generated
     */
    EstateProtection selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_protection
     *
     * @mbg.generated
     */
    List<EstateProtection> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_protection
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(EstateProtection record);
}