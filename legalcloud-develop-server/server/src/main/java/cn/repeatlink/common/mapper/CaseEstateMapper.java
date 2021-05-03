package cn.repeatlink.common.mapper;

import cn.repeatlink.common.entity.CaseEstate;
import java.util.List;

public interface CaseEstateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_estate
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String caseId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_estate
     *
     * @mbg.generated
     */
    int insert(CaseEstate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_estate
     *
     * @mbg.generated
     */
    CaseEstate selectByPrimaryKey(String caseId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_estate
     *
     * @mbg.generated
     */
    List<CaseEstate> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_estate
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CaseEstate record);
}