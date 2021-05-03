package cn.repeatlink.framework.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页数据
 * 
 */
@ApiModel("分页信息")
@Data
public class PageDomain<T> {
	public  static final int DEFALUT_PAGE_SIZE=10;
	@ApiModelProperty("当前页")
	private Integer current_page;
	@ApiModelProperty("每页显示记录数")
	private Integer page_size=DEFALUT_PAGE_SIZE;
	@ApiModelProperty("用于排序的列的属性名")
	private String field;
	@ApiModelProperty("asc：升序排列，desc:降序排列;默认使用升序")
	private String  order;
	@ApiModelProperty("查询条件")
	private T searchConditon;

	

}
