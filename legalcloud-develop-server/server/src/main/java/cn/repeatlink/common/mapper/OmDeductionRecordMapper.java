package cn.repeatlink.common.mapper;

import cn.repeatlink.common.entity.OmDeductionRecord;
import java.util.List;

public interface OmDeductionRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table om_deduction_record
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table om_deduction_record
     *
     * @mbg.generated
     */
    int insert(OmDeductionRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table om_deduction_record
     *
     * @mbg.generated
     */
    OmDeductionRecord selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table om_deduction_record
     *
     * @mbg.generated
     */
    List<OmDeductionRecord> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table om_deduction_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(OmDeductionRecord record);
}