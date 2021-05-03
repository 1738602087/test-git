package com.itheima.boot.dao;

import java.util.List;

import com.itheima.boot.pojo.BaseDict;

public interface BaseDictDao {

	List<BaseDict> getDictListByTypeCode(String typeCode);
}
