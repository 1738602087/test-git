/**
 * 
 */
package cn.repeatlink.common.bean;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @param <T>
 * @date 2020-09-21 13:38
 */

@Data
@ApiModel("分页信息（加载更多）")
public class PageLoadMoreVo<T, P> {
	
	@ApiModelProperty("当前数据最后一条记录的时间")
	private String time;
	
	@ApiModelProperty("当前数据最后一条记录的唯一标识")
	private String id;
	
	@ApiModelProperty("当前数据集合")
	private List<T> datas;
	
	@ApiModelProperty("当前数据的检索条件")
	private P search_vo;
	
	private int size = 20;

}
