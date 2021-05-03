package cn.repeatlink.common.mapper;

import cn.repeatlink.common.entity.EstateUser;
import java.util.List;

public interface EstateUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_user
     *
     * @mbg.generated
     */
    int insert(EstateUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_user
     *
     * @mbg.generated
     */
    EstateUser selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_user
     *
     * @mbg.generated
     */
    List<EstateUser> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table estate_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(EstateUser record);
}