package cn.repeatlink.common.entity;

import java.io.Serializable;
import java.util.Date;

public class LawGroup implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.group_id
     *
     * @mbg.generated
     */
    private String groupId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.group_name
     *
     * @mbg.generated
     */
    private String groupName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.addr
     *
     * @mbg.generated
     */
    private String addr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.representer
     *
     * @mbg.generated
     */
    private String representer;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.representer_kana
     *
     * @mbg.generated
     */
    private String representerKana;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.staff
     *
     * @mbg.generated
     */
    private String staff;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.tel
     *
     * @mbg.generated
     */
    private String tel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.email
     *
     * @mbg.generated
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.applicant_email
     *
     * @mbg.generated
     */
    private String applicantEmail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.status
     *
     * @mbg.generated
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column law_group.photo
     *
     * @mbg.generated
     */
    private byte[] photo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table law_group
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.group_id
     *
     * @return the value of law_group.group_id
     *
     * @mbg.generated
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.group_id
     *
     * @param groupId the value for law_group.group_id
     *
     * @mbg.generated
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.group_name
     *
     * @return the value of law_group.group_name
     *
     * @mbg.generated
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.group_name
     *
     * @param groupName the value for law_group.group_name
     *
     * @mbg.generated
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.addr
     *
     * @return the value of law_group.addr
     *
     * @mbg.generated
     */
    public String getAddr() {
        return addr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.addr
     *
     * @param addr the value for law_group.addr
     *
     * @mbg.generated
     */
    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.representer
     *
     * @return the value of law_group.representer
     *
     * @mbg.generated
     */
    public String getRepresenter() {
        return representer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.representer
     *
     * @param representer the value for law_group.representer
     *
     * @mbg.generated
     */
    public void setRepresenter(String representer) {
        this.representer = representer == null ? null : representer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.representer_kana
     *
     * @return the value of law_group.representer_kana
     *
     * @mbg.generated
     */
    public String getRepresenterKana() {
        return representerKana;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.representer_kana
     *
     * @param representerKana the value for law_group.representer_kana
     *
     * @mbg.generated
     */
    public void setRepresenterKana(String representerKana) {
        this.representerKana = representerKana == null ? null : representerKana.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.staff
     *
     * @return the value of law_group.staff
     *
     * @mbg.generated
     */
    public String getStaff() {
        return staff;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.staff
     *
     * @param staff the value for law_group.staff
     *
     * @mbg.generated
     */
    public void setStaff(String staff) {
        this.staff = staff == null ? null : staff.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.tel
     *
     * @return the value of law_group.tel
     *
     * @mbg.generated
     */
    public String getTel() {
        return tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.tel
     *
     * @param tel the value for law_group.tel
     *
     * @mbg.generated
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.email
     *
     * @return the value of law_group.email
     *
     * @mbg.generated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.email
     *
     * @param email the value for law_group.email
     *
     * @mbg.generated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.applicant_email
     *
     * @return the value of law_group.applicant_email
     *
     * @mbg.generated
     */
    public String getApplicantEmail() {
        return applicantEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.applicant_email
     *
     * @param applicantEmail the value for law_group.applicant_email
     *
     * @mbg.generated
     */
    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail == null ? null : applicantEmail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.status
     *
     * @return the value of law_group.status
     *
     * @mbg.generated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.status
     *
     * @param status the value for law_group.status
     *
     * @mbg.generated
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.remark
     *
     * @return the value of law_group.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.remark
     *
     * @param remark the value for law_group.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.create_time
     *
     * @return the value of law_group.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.create_time
     *
     * @param createTime the value for law_group.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.update_time
     *
     * @return the value of law_group.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.update_time
     *
     * @param updateTime the value for law_group.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column law_group.photo
     *
     * @return the value of law_group.photo
     *
     * @mbg.generated
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column law_group.photo
     *
     * @param photo the value for law_group.photo
     *
     * @mbg.generated
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table law_group
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", groupId=").append(groupId);
        sb.append(", groupName=").append(groupName);
        sb.append(", addr=").append(addr);
        sb.append(", representer=").append(representer);
        sb.append(", representerKana=").append(representerKana);
        sb.append(", staff=").append(staff);
        sb.append(", tel=").append(tel);
        sb.append(", email=").append(email);
        sb.append(", applicantEmail=").append(applicantEmail);
        sb.append(", status=").append(status);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", photo=").append(photo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}