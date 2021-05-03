package cn.repeatlink.common.entity;

import java.io.Serializable;
import java.util.Date;

public class SysMenu implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.menu_id
     *
     * @mbg.generated
     */
    private Long menuId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.menu_name
     *
     * @mbg.generated
     */
    private String menuName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.parent_id
     *
     * @mbg.generated
     */
    private Long parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.order_num
     *
     * @mbg.generated
     */
    private Integer orderNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.path
     *
     * @mbg.generated
     */
    private String path;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.component
     *
     * @mbg.generated
     */
    private String component;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.is_frame
     *
     * @mbg.generated
     */
    private Integer isFrame;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.menu_type
     *
     * @mbg.generated
     */
    private String menuType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.visible
     *
     * @mbg.generated
     */
    private Short visible;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.perms
     *
     * @mbg.generated
     */
    private String perms;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.icon
     *
     * @mbg.generated
     */
    private String icon;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.status
     *
     * @mbg.generated
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.create_by
     *
     * @mbg.generated
     */
    private Long createBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.update_by
     *
     * @mbg.generated
     */
    private Long updateBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column base..sys_menu.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table base..sys_menu
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.menu_id
     *
     * @return the value of base..sys_menu.menu_id
     *
     * @mbg.generated
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.menu_id
     *
     * @param menuId the value for base..sys_menu.menu_id
     *
     * @mbg.generated
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.menu_name
     *
     * @return the value of base..sys_menu.menu_name
     *
     * @mbg.generated
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.menu_name
     *
     * @param menuName the value for base..sys_menu.menu_name
     *
     * @mbg.generated
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.parent_id
     *
     * @return the value of base..sys_menu.parent_id
     *
     * @mbg.generated
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.parent_id
     *
     * @param parentId the value for base..sys_menu.parent_id
     *
     * @mbg.generated
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.order_num
     *
     * @return the value of base..sys_menu.order_num
     *
     * @mbg.generated
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.order_num
     *
     * @param orderNum the value for base..sys_menu.order_num
     *
     * @mbg.generated
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.path
     *
     * @return the value of base..sys_menu.path
     *
     * @mbg.generated
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.path
     *
     * @param path the value for base..sys_menu.path
     *
     * @mbg.generated
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.component
     *
     * @return the value of base..sys_menu.component
     *
     * @mbg.generated
     */
    public String getComponent() {
        return component;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.component
     *
     * @param component the value for base..sys_menu.component
     *
     * @mbg.generated
     */
    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.is_frame
     *
     * @return the value of base..sys_menu.is_frame
     *
     * @mbg.generated
     */
    public Integer getIsFrame() {
        return isFrame;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.is_frame
     *
     * @param isFrame the value for base..sys_menu.is_frame
     *
     * @mbg.generated
     */
    public void setIsFrame(Integer isFrame) {
        this.isFrame = isFrame;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.menu_type
     *
     * @return the value of base..sys_menu.menu_type
     *
     * @mbg.generated
     */
    public String getMenuType() {
        return menuType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.menu_type
     *
     * @param menuType the value for base..sys_menu.menu_type
     *
     * @mbg.generated
     */
    public void setMenuType(String menuType) {
        this.menuType = menuType == null ? null : menuType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.visible
     *
     * @return the value of base..sys_menu.visible
     *
     * @mbg.generated
     */
    public Short getVisible() {
        return visible;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.visible
     *
     * @param visible the value for base..sys_menu.visible
     *
     * @mbg.generated
     */
    public void setVisible(Short visible) {
        this.visible = visible;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.perms
     *
     * @return the value of base..sys_menu.perms
     *
     * @mbg.generated
     */
    public String getPerms() {
        return perms;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.perms
     *
     * @param perms the value for base..sys_menu.perms
     *
     * @mbg.generated
     */
    public void setPerms(String perms) {
        this.perms = perms == null ? null : perms.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.icon
     *
     * @return the value of base..sys_menu.icon
     *
     * @mbg.generated
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.icon
     *
     * @param icon the value for base..sys_menu.icon
     *
     * @mbg.generated
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.status
     *
     * @return the value of base..sys_menu.status
     *
     * @mbg.generated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.status
     *
     * @param status the value for base..sys_menu.status
     *
     * @mbg.generated
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.create_by
     *
     * @return the value of base..sys_menu.create_by
     *
     * @mbg.generated
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.create_by
     *
     * @param createBy the value for base..sys_menu.create_by
     *
     * @mbg.generated
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.create_time
     *
     * @return the value of base..sys_menu.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.create_time
     *
     * @param createTime the value for base..sys_menu.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.update_by
     *
     * @return the value of base..sys_menu.update_by
     *
     * @mbg.generated
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.update_by
     *
     * @param updateBy the value for base..sys_menu.update_by
     *
     * @mbg.generated
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.update_time
     *
     * @return the value of base..sys_menu.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.update_time
     *
     * @param updateTime the value for base..sys_menu.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column base..sys_menu.remark
     *
     * @return the value of base..sys_menu.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column base..sys_menu.remark
     *
     * @param remark the value for base..sys_menu.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_menu
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", menuId=").append(menuId);
        sb.append(", menuName=").append(menuName);
        sb.append(", parentId=").append(parentId);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", path=").append(path);
        sb.append(", component=").append(component);
        sb.append(", isFrame=").append(isFrame);
        sb.append(", menuType=").append(menuType);
        sb.append(", visible=").append(visible);
        sb.append(", perms=").append(perms);
        sb.append(", icon=").append(icon);
        sb.append(", status=").append(status);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}