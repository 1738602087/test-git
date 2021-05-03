package cn.repeatlink.common.service;

import cn.repeatlink.common.bean.DataTableHeader;

public interface IDatatableHeaderService {
	
	DataTableHeader getDataTableHeader(String module,String tableName);
}
