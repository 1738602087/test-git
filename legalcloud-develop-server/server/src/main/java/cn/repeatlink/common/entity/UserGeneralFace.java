package cn.repeatlink.common.entity;

import java.io.Serializable;
import java.util.Date;

public class UserGeneralFace implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_general_face.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_general_face.user_general_id
     *
     * @mbg.generated
     */
    private String userGeneralId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_general_face.face_id
     *
     * @mbg.generated
     */
    private String faceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_general_face.collection_id
     *
     * @mbg.generated
     */
    private String collectionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_general_face.status
     *
     * @mbg.generated
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_general_face.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_general_face.created_by
     *
     * @mbg.generated
     */
    private String createdBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_general_face.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_general_face.updated_by
     *
     * @mbg.generated
     */
    private String updatedBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_general_face.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_general_face.face_data
     *
     * @mbg.generated
     */
    private byte[] faceData;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_general_face
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_general_face.id
     *
     * @return the value of user_general_face.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_general_face.id
     *
     * @param id the value for user_general_face.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_general_face.user_general_id
     *
     * @return the value of user_general_face.user_general_id
     *
     * @mbg.generated
     */
    public String getUserGeneralId() {
        return userGeneralId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_general_face.user_general_id
     *
     * @param userGeneralId the value for user_general_face.user_general_id
     *
     * @mbg.generated
     */
    public void setUserGeneralId(String userGeneralId) {
        this.userGeneralId = userGeneralId == null ? null : userGeneralId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_general_face.face_id
     *
     * @return the value of user_general_face.face_id
     *
     * @mbg.generated
     */
    public String getFaceId() {
        return faceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_general_face.face_id
     *
     * @param faceId the value for user_general_face.face_id
     *
     * @mbg.generated
     */
    public void setFaceId(String faceId) {
        this.faceId = faceId == null ? null : faceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_general_face.collection_id
     *
     * @return the value of user_general_face.collection_id
     *
     * @mbg.generated
     */
    public String getCollectionId() {
        return collectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_general_face.collection_id
     *
     * @param collectionId the value for user_general_face.collection_id
     *
     * @mbg.generated
     */
    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId == null ? null : collectionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_general_face.status
     *
     * @return the value of user_general_face.status
     *
     * @mbg.generated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_general_face.status
     *
     * @param status the value for user_general_face.status
     *
     * @mbg.generated
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_general_face.remark
     *
     * @return the value of user_general_face.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_general_face.remark
     *
     * @param remark the value for user_general_face.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_general_face.created_by
     *
     * @return the value of user_general_face.created_by
     *
     * @mbg.generated
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_general_face.created_by
     *
     * @param createdBy the value for user_general_face.created_by
     *
     * @mbg.generated
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_general_face.create_time
     *
     * @return the value of user_general_face.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_general_face.create_time
     *
     * @param createTime the value for user_general_face.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_general_face.updated_by
     *
     * @return the value of user_general_face.updated_by
     *
     * @mbg.generated
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_general_face.updated_by
     *
     * @param updatedBy the value for user_general_face.updated_by
     *
     * @mbg.generated
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_general_face.update_time
     *
     * @return the value of user_general_face.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_general_face.update_time
     *
     * @param updateTime the value for user_general_face.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_general_face.face_data
     *
     * @return the value of user_general_face.face_data
     *
     * @mbg.generated
     */
    public byte[] getFaceData() {
        return faceData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_general_face.face_data
     *
     * @param faceData the value for user_general_face.face_data
     *
     * @mbg.generated
     */
    public void setFaceData(byte[] faceData) {
        this.faceData = faceData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_general_face
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
        sb.append(", userGeneralId=").append(userGeneralId);
        sb.append(", faceId=").append(faceId);
        sb.append(", collectionId=").append(collectionId);
        sb.append(", status=").append(status);
        sb.append(", remark=").append(remark);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", faceData=").append(faceData);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}