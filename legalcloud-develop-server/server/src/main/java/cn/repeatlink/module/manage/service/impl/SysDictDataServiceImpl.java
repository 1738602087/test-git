package cn.repeatlink.module.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.repeatlink.common.Constant;
import cn.repeatlink.common.entity.SysDictData;
import cn.repeatlink.common.mapper.SysDictDataMapper;
import cn.repeatlink.framework.exception.BaseRuntimeException;
import cn.repeatlink.module.manage.common.ManagerResultCode;
import cn.repeatlink.module.manage.dto.DictDataInfo;
import cn.repeatlink.module.manage.service.ISysDictDataService;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SysDictDataServiceImpl implements ISysDictDataService {

	@Autowired
	private SysDictDataMapper sysDictDataMapper;
	
	@Override
	public List<SysDictData> findAll() {
		return sysDictDataMapper.selectAll();
	}


	@Override
	public List<DictDataInfo> search(DictDataInfo dictInfo) {
		List<DictDataInfo> infos = this.sysDictDataMapper.search(dictInfo);
		if(infos!=null) { 
			
		}
		return infos;
	}
	
	@Override
	public DictDataInfo addDictData(DictDataInfo dictInfo, Long operUserId) {
		validateDictDataInfo(dictInfo);

		SysDictData dict = new SysDictData();
		dict.setDictLabel(dictInfo.getDictLabel());
		dict.setDictValue(dictInfo.getDictValue());
		dict.setDictType(dictInfo.getDictType());
		dict.setDictSort(dictInfo.getDictSort());
		dict.setCssClass(dictInfo.getCssClass());
		dict.setListClass(dictInfo.getListClass());
		dict.setRemark(dictInfo.getRemark());
		dict.setIsDefault(dictInfo.getIsDefault());
		dict.setStatus(Constant.STATUS_VALID);
		dict.setCreateBy(operUserId);
		dict.setCreateTime(new Date());
		sysDictDataMapper.insert(dict);

		dictInfo.setDictCode(dict.getDictCode());
		return dictInfo;
	}
	
	@Override
	public DictDataInfo findByCode(Long dictCode) {
		SysDictData dict = sysDictDataMapper.selectByPrimaryKey(dictCode);
		if (dict == null) {
			throw new BaseRuntimeException(ManagerResultCode.USER_USER_ID_INVALID);
		}
		DictDataInfo result = new DictDataInfo();
		result.setDictCode(dict.getDictCode());
		result.setDictLabel(dict.getDictLabel());
		result.setDictValue(dict.getDictValue());
		result.setStatus(dict.getStatus());
		result.setDictType(dict.getDictType());
		result.setIsDefault(dict.getIsDefault());
		result.setRemark(dict.getRemark());
		result.setCssClass(dict.getCssClass());
		result.setListClass(dict.getListClass());
		return result;
	}
	
	
	/**
	 * 校验dict 信息输入内容
	 * 
	 * @param userInfo
	 */
	private void validateDictDataInfo(DictDataInfo dictInfo) {
		if (dictInfo == null) {
			throw new BaseRuntimeException(ManagerResultCode.USER_INFO_NULL);
		}

		if (StringUtils.isEmpty(dictInfo.getDictLabel())) {
			throw new BaseRuntimeException(ManagerResultCode.USER_NAME_NULL);
		}
		if (dictInfo.getDictLabel().length() < 2 || dictInfo.getDictLabel().length() > 50) {
			throw new BaseRuntimeException(ManagerResultCode.USER_NAME_INVALID);
		}

	}

}
