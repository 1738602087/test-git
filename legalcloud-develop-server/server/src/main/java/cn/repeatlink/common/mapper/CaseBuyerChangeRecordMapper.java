package cn.repeatlink.common.mapper;

import cn.repeatlink.common.entity.CaseBuyerChangeRecord;
import java.util.List;

public interface CaseBuyerChangeRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_buyer_change_record
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_buyer_change_record
     *
     * @mbg.generated
     */
    int insert(CaseBuyerChangeRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_buyer_change_record
     *
     * @mbg.generated
     */
    CaseBuyerChangeRecord selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_buyer_change_record
     *
     * @mbg.generated
     */
    List<CaseBuyerChangeRecord> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_buyer_change_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CaseBuyerChangeRecord record);
}