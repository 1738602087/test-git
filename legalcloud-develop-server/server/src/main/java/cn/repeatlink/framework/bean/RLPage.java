package cn.repeatlink.framework.bean;

import java.util.List;

import com.github.pagehelper.Page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页查询结果")
public class RLPage<T> {
	
	  /**
     * 页码，从1开始
     */
    @ApiModelProperty("当前页码，从1开始")
    private int current_page;
    /**
     * 页面大小
     */
    @ApiModelProperty("每页记录数")
    private int page_size;
    /**
     * 起始行
     */
    @ApiModelProperty("起始行")
    private int start_row;
    /**
     * 末行
     */
    @ApiModelProperty(" 末行")
    private int end_row;
    /**
     * 总数
     */
    @ApiModelProperty("总数")
    private long records_total;
    /**
     * 总页数
     */
    @ApiModelProperty("总页数")
    private int pages_total;

    @ApiModelProperty("当前页记录")
    private List<T> download_list;
    
    public RLPage(Page<T> datas ) {
    	this.current_page=datas.getPageNum();
    	this.page_size=datas.getPageSize();
    	this.start_row=datas.getStartRow();
    	this.pages_total=datas.getPages();
    	this.end_row=datas.getEndRow();
    	this.records_total=datas.getTotal();
    	this.download_list=datas;
    }
}
 