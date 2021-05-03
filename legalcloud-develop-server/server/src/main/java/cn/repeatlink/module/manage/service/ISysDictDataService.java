package cn.repeatlink.module.manage.service;

import java.util.List;

import cn.repeatlink.common.entity.SysDictData;
import cn.repeatlink.module.manage.dto.DictDataInfo;

public interface ISysDictDataService {

	
	List<SysDictData> findAll();
	
	/**
	 * 根据条件查询字典
	 * @param userInfo
	 * @return
	 */
	List<DictDataInfo> search(DictDataInfo dictInfo);

	/**
	 * 添加字典
	 * @param roleInfo
	 * @param operUserId
	 * @return
	 */
	DictDataInfo addDictData(DictDataInfo dictInfo, Long operUserId);

	/**
	 * 根据CODE查找字典
	 * @param roleId
	 * @return
	 */
	DictDataInfo findByCode(Long dictCode);
	
	
	
}
