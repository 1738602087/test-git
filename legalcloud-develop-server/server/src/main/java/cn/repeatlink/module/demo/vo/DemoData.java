package cn.repeatlink.module.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户数据")
@Data
public class DemoData {
	@ApiModelProperty("用户Id")
	Long id;
	@ApiModelProperty("用户姓名")
	String name;
	@ApiModelProperty("用户年龄")
	int age;
	
	
}
