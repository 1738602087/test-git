package cn.repeatlink.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("表头信息")
@Data
public class HeaderVo {
	@ApiModelProperty("显示文字")
	private String title;
	@ApiModelProperty("是否可以排序")
	private Boolean sorter;
	@ApiModelProperty("对应对象中的属性")
	private String dataIndex;
	@ApiModelProperty("类型")
	private String _format;
	
}
