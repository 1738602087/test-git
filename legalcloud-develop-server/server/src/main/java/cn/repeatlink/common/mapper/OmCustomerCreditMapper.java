package cn.repeatlink.common.mapper;

import cn.repeatlink.common.entity.OmCustomerCredit;
import java.util.List;

public interface OmCustomerCreditMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table om_customer_credit
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table om_customer_credit
     *
     * @mbg.generated
     */
    int insert(OmCustomerCredit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table om_customer_credit
     *
     * @mbg.generated
     */
    OmCustomerCredit selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table om_customer_credit
     *
     * @mbg.generated
     */
    List<OmCustomerCredit> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table om_customer_credit
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(OmCustomerCredit record);
}