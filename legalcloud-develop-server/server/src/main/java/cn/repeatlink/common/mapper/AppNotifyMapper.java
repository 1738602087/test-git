package cn.repeatlink.common.mapper;

import cn.repeatlink.common.entity.AppNotify;
import java.util.List;

public interface AppNotifyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_notify
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String notifyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_notify
     *
     * @mbg.generated
     */
    int insert(AppNotify record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_notify
     *
     * @mbg.generated
     */
    AppNotify selectByPrimaryKey(String notifyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_notify
     *
     * @mbg.generated
     */
    List<AppNotify> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_notify
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AppNotify record);
}