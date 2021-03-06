package cn.repeatlink.common.entity;

import java.io.Serializable;
import java.util.Date;

public class CaseBuyerChangeRecord implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.case_id
     *
     * @mbg.generated
     */
    private String caseId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.user_general_id
     *
     * @mbg.generated
     */
    private String userGeneralId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.field_name
     *
     * @mbg.generated
     */
    private String fieldName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.field_type
     *
     * @mbg.generated
     */
    private String fieldType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.action_time
     *
     * @mbg.generated
     */
    private Date actionTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.action_by
     *
     * @mbg.generated
     */
    private String actionBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.status
     *
     * @mbg.generated
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.created_by
     *
     * @mbg.generated
     */
    private String createdBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.updated_by
     *
     * @mbg.generated
     */
    private String updatedBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.value_before
     *
     * @mbg.generated
     */
    private String valueBefore;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column case_buyer_change_record.value_after
     *
     * @mbg.generated
     */
    private String valueAfter;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table case_buyer_change_record
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.id
     *
     * @return the value of case_buyer_change_record.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.id
     *
     * @param id the value for case_buyer_change_record.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.case_id
     *
     * @return the value of case_buyer_change_record.case_id
     *
     * @mbg.generated
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.case_id
     *
     * @param caseId the value for case_buyer_change_record.case_id
     *
     * @mbg.generated
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId == null ? null : caseId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.user_general_id
     *
     * @return the value of case_buyer_change_record.user_general_id
     *
     * @mbg.generated
     */
    public String getUserGeneralId() {
        return userGeneralId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.user_general_id
     *
     * @param userGeneralId the value for case_buyer_change_record.user_general_id
     *
     * @mbg.generated
     */
    public void setUserGeneralId(String userGeneralId) {
        this.userGeneralId = userGeneralId == null ? null : userGeneralId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.field_name
     *
     * @return the value of case_buyer_change_record.field_name
     *
     * @mbg.generated
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.field_name
     *
     * @param fieldName the value for case_buyer_change_record.field_name
     *
     * @mbg.generated
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.field_type
     *
     * @return the value of case_buyer_change_record.field_type
     *
     * @mbg.generated
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.field_type
     *
     * @param fieldType the value for case_buyer_change_record.field_type
     *
     * @mbg.generated
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType == null ? null : fieldType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.action_time
     *
     * @return the value of case_buyer_change_record.action_time
     *
     * @mbg.generated
     */
    public Date getActionTime() {
        return actionTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.action_time
     *
     * @param actionTime the value for case_buyer_change_record.action_time
     *
     * @mbg.generated
     */
    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.action_by
     *
     * @return the value of case_buyer_change_record.action_by
     *
     * @mbg.generated
     */
    public String getActionBy() {
        return actionBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.action_by
     *
     * @param actionBy the value for case_buyer_change_record.action_by
     *
     * @mbg.generated
     */
    public void setActionBy(String actionBy) {
        this.actionBy = actionBy == null ? null : actionBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.status
     *
     * @return the value of case_buyer_change_record.status
     *
     * @mbg.generated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.status
     *
     * @param status the value for case_buyer_change_record.status
     *
     * @mbg.generated
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.created_by
     *
     * @return the value of case_buyer_change_record.created_by
     *
     * @mbg.generated
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.created_by
     *
     * @param createdBy the value for case_buyer_change_record.created_by
     *
     * @mbg.generated
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.create_time
     *
     * @return the value of case_buyer_change_record.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.create_time
     *
     * @param createTime the value for case_buyer_change_record.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.updated_by
     *
     * @return the value of case_buyer_change_record.updated_by
     *
     * @mbg.generated
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.updated_by
     *
     * @param updatedBy the value for case_buyer_change_record.updated_by
     *
     * @mbg.generated
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.update_time
     *
     * @return the value of case_buyer_change_record.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.update_time
     *
     * @param updateTime the value for case_buyer_change_record.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.value_before
     *
     * @return the value of case_buyer_change_record.value_before
     *
     * @mbg.generated
     */
    public String getValueBefore() {
        return valueBefore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.value_before
     *
     * @param valueBefore the value for case_buyer_change_record.value_before
     *
     * @mbg.generated
     */
    public void setValueBefore(String valueBefore) {
        this.valueBefore = valueBefore == null ? null : valueBefore.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column case_buyer_change_record.value_after
     *
     * @return the value of case_buyer_change_record.value_after
     *
     * @mbg.generated
     */
    public String getValueAfter() {
        return valueAfter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column case_buyer_change_record.value_after
     *
     * @param valueAfter the value for case_buyer_change_record.value_after
     *
     * @mbg.generated
     */
    public void setValueAfter(String valueAfter) {
        this.valueAfter = valueAfter == null ? null : valueAfter.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table case_buyer_change_record
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", caseId=").append(caseId);
        sb.append(", userGeneralId=").append(userGeneralId);
        sb.append(", fieldName=").append(fieldName);
        sb.append(", fieldType=").append(fieldType);
        sb.append(", actionTime=").append(actionTime);
        sb.append(", actionBy=").append(actionBy);
        sb.append(", status=").append(status);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", valueBefore=").append(valueBefore);
        sb.append(", valueAfter=").append(valueAfter);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}