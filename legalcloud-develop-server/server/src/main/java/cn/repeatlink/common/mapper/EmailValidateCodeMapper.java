package cn.repeatlink.common.mapper;

import cn.repeatlink.common.entity.EmailValidateCode;
import java.util.List;

public interface EmailValidateCodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email_validate_code
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email_validate_code
     *
     * @mbg.generated
     */
    int insert(EmailValidateCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email_validate_code
     *
     * @mbg.generated
     */
    EmailValidateCode selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email_validate_code
     *
     * @mbg.generated
     */
    List<EmailValidateCode> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table email_validate_code
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(EmailValidateCode record);
}