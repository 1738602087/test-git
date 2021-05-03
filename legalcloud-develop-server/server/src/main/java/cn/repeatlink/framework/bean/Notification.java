package cn.repeatlink.framework.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lsq
 * @date 2020-12-3 10:41
 */

@ApiModel("推送类型")
@Data
public class Notification {

    //推送通知初始化类型
    public static final String All_NotificationType = "all";
    public static final String Not_NotificationType = "null";

    //定义推送通知类型
    public static final String TYPE_A = "type_a";
    public static final String TYPE_B = "type_b";
    public static final String TYPE_C = "type_c";

    //定义推送标题
    public static final String TITLE_A = "通知A";

    @ApiModelProperty("推送内容")
    private String alert;
    @ApiModelProperty("推送类型")
    private String type;
    @ApiModelProperty("标题")
    private String title;
}
