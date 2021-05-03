package cn.repeatlink.module.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.SysDept;
import cn.repeatlink.common.mapper.SysDeptMapper;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.module.manage.common.Constant.DeptDelFlag;
import cn.repeatlink.module.manage.common.ManagerResultCode;
import cn.repeatlink.module.manage.dto.DeptInfo;
import cn.repeatlink.module.manage.service.ISysDeptService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SysDeptServiceImpl implements ISysDeptService {

	@Autowired
	private SysDeptMapper sysDeptMapper;
	
	@Override
	public List<SysDept> findAll() {
		return sysDeptMapper.selectAll();
	}


	@Override
	public List<DeptInfo> search(DeptInfo deptInfo) {
		List<DeptInfo> infos = this.sysDeptMapper.search(deptInfo);
		if(infos!=null) { 
			
		}
		return infos;
	}
	
	@Override
	public DeptInfo addDept(DeptInfo deptInfo, Long operUserId) {
		validateDeptInfo(deptInfo);

		SysDept dept = new SysDept();
		dept.setParentId(deptInfo.getParentId());
		dept.setAncestors(deptInfo.getAncestors());
		dept.setDeptName(deptInfo.getDeptName());
		dept.setDelFlag(DeptDelFlag.NOT_DEL);
		dept.setStatus(Constant.STATUS_VALID);
		dept.setCreateBy(operUserId);
		dept.setCreateTime(new Date());
		sysDeptMapper.insert(dept);

		deptInfo.setDeptId(dept.getDeptId());
		return deptInfo;
	}
	
	@Override
	public DeptInfo findById(Long deptId) {
		SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
		if (dept == null) {
			throw new BaseRuntimeException(ManagerResultCode.USER_USER_ID_INVALID);
		}
		DeptInfo result = new DeptInfo();
		result.setDeptId(dept.getDeptId());
		result.setDeptName(dept.getDeptName());
		result.setAncestors(dept.getAncestors());
		result.setStatus(dept.getStatus());
		result.setParentId(dept.getParentId());
		result.setLeader(dept.getLeader());
		result.setDelFlag(dept.getDelFlag());
		result.setEmail(dept.getEmail());
		result.setPhone(dept.getPhone());
		return result;
	}
	
	
	/**
	 * 校验dept 信息输入内容
	 * 
	 * @param userInfo
	 */
	private void validateDeptInfo(DeptInfo deptInfo) {
		if (deptInfo == null) {
			throw new BaseRuntimeException(ManagerResultCode.USER_INFO_NULL);
		}

		if (StringUtils.isEmpty(deptInfo.getDeptName())) {
			throw new BaseRuntimeException(ManagerResultCode.USER_NAME_NULL);
		}
		if (deptInfo.getDeptName().length() < 2 || deptInfo.getDeptName().length() > 50) {
			throw new BaseRuntimeException(ManagerResultCode.USER_NAME_INVALID);
		}

	}

}
