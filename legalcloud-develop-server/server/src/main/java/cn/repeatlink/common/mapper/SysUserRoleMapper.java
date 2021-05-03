package cn.repeatlink.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.repeatlink.common.entity.SysUserRole;

public interface SysUserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_user_role
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_user_role
     *
     * @mbg.generated
     */
    int insert(SysUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_user_role
     *
     * @mbg.generated
     */
    SysUserRole selectByPrimaryKey(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_user_role
     *
     * @mbg.generated
     */
    List<SysUserRole> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table base..sys_user_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysUserRole record);
    
    SysUserRole selectByUserId(@Param("userId") Long userId);

	int updateByUserId(SysUserRole record);
}