package cn.repeatlink.common.mapper;

import cn.repeatlink.common.entity.CaseSeller;
import java.util.List;

public interface CaseSellerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_seller
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_seller
     *
     * @mbg.generated
     */
    int insert(CaseSeller record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_seller
     *
     * @mbg.generated
     */
    CaseSeller selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_seller
     *
     * @mbg.generated
     */
    List<CaseSeller> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_seller
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CaseSeller record);
}