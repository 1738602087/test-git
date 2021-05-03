package cn.repeatlink.module.general.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lsq
 * @date 2020-12-14 11:28
 */
@Data
@ApiModel("用户附加属性")
public class UserAttributeInfo {

    @ApiModelProperty("职位")
    private String position;

    @ApiModelProperty("0：未婚，1：既婚，2：離婚")
    private String marriage;

    @ApiModelProperty("子女数量")
    private String children;
    
    @ApiModelProperty("电话番号")
    private String phone_number;
}
