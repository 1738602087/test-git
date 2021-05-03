package cn.repeatlink.framework.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("按钮状态信息")
public class ButtonVo {
    @ApiModelProperty("按钮true/false")
    private String all;
    @ApiModelProperty("按钮true/false")
    private String sys;

    public ButtonVo(String all, String sys) {
        this.all = all;
        this.sys = sys;
    }

    public ButtonVo() {
    }
}
