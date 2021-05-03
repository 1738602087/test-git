package cn.repeatlink.module.manage.service;

import java.util.List;

import cn.repeatlink.common.entity.SysDept;
import cn.repeatlink.module.manage.dto.DeptInfo;

public interface ISysDeptService {

	
	List<SysDept> findAll();
	
	/**
	 * 根据条件查询部门
	 * @param userInfo
	 * @return
	 */
	List<DeptInfo> search(DeptInfo deptInfo);

	/**
	 * 添加部门
	 * @param roleInfo
	 * @param operUserId
	 * @return
	 */
	DeptInfo addDept(DeptInfo deptInfo, Long operUserId);

	/**
	 * 根据ID查找部门
	 * @param roleId
	 * @return
	 */
	DeptInfo findById(Long deptId);
	
	
	
}
