package cn.repeatlink.common.entity;

import java.io.Serializable;

public class SysDataTableHeader implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_data_table_title.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_data_table_title.module
     *
     * @mbg.generated
     */
    private String module;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_data_table_title.data_table_name
     *
     * @mbg.generated
     */
    private String dataTableName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_data_table_title.data_index
     *
     * @mbg.generated
     */
    private String dataIndex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_data_table_title.title_key
     *
     * @mbg.generated
     */
    private String titleKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_data_table_title.format
     *
     * @mbg.generated
     */
    private String format;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_data_table_title.sortable
     *
     * @mbg.generated
     */
    private Boolean sortable;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_data_table_title.order_no
     *
     * @mbg.generated
     */
    private Integer orderNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_data_table_title.status
     *
     * @mbg.generated
     */
    private Short status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table base..sys_data_table_title
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_data_table_title.id
     *
     * @return the value of base..sys_data_table_title.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_data_table_title.id
     *
     * @param id the value for base..sys_data_table_title.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_data_table_title.module
     *
     * @return the value of base..sys_data_table_title.module
     *
     * @mbg.generated
     */
    public String getModule() {
        return module;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_data_table_title.module
     *
     * @param module the value for base..sys_data_table_title.module
     *
     * @mbg.generated
     */
    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_data_table_title.data_table_name
     *
     * @return the value of base..sys_data_table_title.data_table_name
     *
     * @mbg.generated
     */
    public String getDataTableName() {
        return dataTableName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_data_table_title.data_table_name
     *
     * @param dataTableName the value for base..sys_data_table_title.data_table_name
     *
     * @mbg.generated
     */
    public void setDataTableName(String dataTableName) {
        this.dataTableName = dataTableName == null ? null : dataTableName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_data_table_title.data_index
     *
     * @return the value of base..sys_data_table_title.data_index
     *
     * @mbg.generated
     */
    public String getDataIndex() {
        return dataIndex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_data_table_title.data_index
     *
     * @param dataIndex the value for base..sys_data_table_title.data_index
     *
     * @mbg.generated
     */
    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex == null ? null : dataIndex.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_data_table_title.title_key
     *
     * @return the value of base..sys_data_table_title.title_key
     *
     * @mbg.generated
     */
    public String getTitleKey() {
        return titleKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_data_table_title.title_key
     *
     * @param titleKey the value for base..sys_data_table_title.title_key
     *
     * @mbg.generated
     */
    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey == null ? null : titleKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_data_table_title.format
     *
     * @return the value of base..sys_data_table_title.format
     *
     * @mbg.generated
     */
    public String getFormat() {
        return format;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_data_table_title.format
     *
     * @param format the value for base..sys_data_table_title.format
     *
     * @mbg.generated
     */
    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_data_table_title.sortable
     *
     * @return the value of base..sys_data_table_title.sortable
     *
     * @mbg.generated
     */
    public Boolean getSortable() {
        return sortable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_data_table_title.sortable
     *
     * @param sortable the value for base..sys_data_table_title.sortable
     *
     * @mbg.generated
     */
    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_data_table_title.order_no
     *
     * @return the value of base..sys_data_table_title.order_no
     *
     * @mbg.generated
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_data_table_title.order_no
     *
     * @param orderNo the value for base..sys_data_table_title.order_no
     *
     * @mbg.generated
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_data_table_title.status
     *
     * @return the value of base..sys_data_table_title.status
     *
     * @mbg.generated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_data_table_title.status
     *
     * @param status the value for base..sys_data_table_title.status
     *
     * @mbg.generated
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_data_table_title
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
        sb.append(", module=").append(module);
        sb.append(", dataTableName=").append(dataTableName);
        sb.append(", dataIndex=").append(dataIndex);
        sb.append(", titleKey=").append(titleKey);
        sb.append(", format=").append(format);
        sb.append(", sortable=").append(sortable);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}