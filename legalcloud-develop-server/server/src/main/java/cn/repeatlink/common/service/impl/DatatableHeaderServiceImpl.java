package cn.repeatlink.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.repeatlink.common.bean.DataTableHeader;
import cn.repeatlink.common.bean.HeaderVo;
import cn.repeatlink.common.entity.SysDataTableHeader;
import cn.repeatlink.common.mapper.SysDataTableHeaderMapper;
import cn.repeatlink.common.service.IDatatableHeaderService;
import lombok.Setter;

@Service

public class DatatableHeaderServiceImpl implements IDatatableHeaderService {
	@Autowired
	@Setter
	private SysDataTableHeaderMapper sysDataTableHeaderMapper;

	@Override
	public DataTableHeader getDataTableHeader(String module, String tableName) {
		
		List<SysDataTableHeader> titles=sysDataTableHeaderMapper.findByModuleAndTableName(module, tableName);
		DataTableHeader tableHeader=new DataTableHeader();
		if(titles==null||titles.size()==0){
			tableHeader.setFile_header( new ArrayList<>());
			return tableHeader;
		}
		
		List<HeaderVo> tableHeaders=new ArrayList<>(); 
		for(int index=0;index<titles.size();index++){
			SysDataTableHeader title=titles.get(index);
			HeaderVo header=new HeaderVo();
			header.set_format(StringUtils.trimToEmpty(title.getFormat()));
			header.setDataIndex(title.getDataIndex());
			header.setSorter(title.getSortable());
			header.setTitle(title.getTitleKey());
			
			tableHeaders.add(header);
		}
		tableHeader.setPage_table_name(tableName);
		tableHeader.setFile_header(tableHeaders);
		return tableHeader;

	}

}
