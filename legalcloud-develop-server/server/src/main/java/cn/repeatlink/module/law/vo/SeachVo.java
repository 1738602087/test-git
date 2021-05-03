package cn.repeatlink.module.law.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api("时间参数")
public class SeachVo {

    @ApiModelProperty("开始时间")
    private String star_time;

    @ApiModelProperty("结束时间")
    private String end_time;
}
