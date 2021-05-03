package cn.repeatlink.common.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("表格表头信息")
public class DataTableHeader {
	@ApiModelProperty("具体的表格表头信息")
	private List<HeaderVo> file_header;
	private String page_table_name;

	public List<HeaderVo> getFile_header() {
		return file_header;
	}

	public void setFile_header(List<HeaderVo> file_header) {
		this.file_header = file_header;
	}
	
	public String getPage_table_name() {
		return page_table_name;
	}

	public void setPage_table_name(String page_table_name) {
		this.page_table_name = page_table_name;
	}

	public DataTableHeader() {
		super();
	}

	public DataTableHeader(List<HeaderVo> file_header) {
		super();
		this.file_header = file_header;
	}

	public DataTableHeader(List<HeaderVo> file_header, String page_table_name) {
		super();
		this.file_header = file_header;
		this.page_table_name = page_table_name;
	}
	
}
