package com.itheima.boot.service;

import java.util.List;

import com.itheima.boot.pojo.BaseDict;

public interface BaseDictService {

	List<BaseDict> getDictListByTypeCode(String typeCode);
}
