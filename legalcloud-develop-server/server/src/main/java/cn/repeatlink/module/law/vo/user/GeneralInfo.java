package cn.repeatlink.module.law.vo.user;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author Lsq
 * @date 2020-12-14 13:36
 */

@Data
@Api("新规登录用户信息")
public class GeneralInfo {

    @ApiModelProperty("唯一ID")
    private String id;

    @ApiModelProperty("顾客名")
    private String userName;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("携带番号")
    private String tel;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("年龄")
    private String age;

    @ApiModelProperty("地址")
    private String addr;

    @ApiModelProperty("职位")
    private String position;

    @ApiModelProperty("婚姻")
    private String marriage;

    @ApiModelProperty("子供人数")
    private String childrens;

    @ApiModelProperty("新规登录日期")
    private String createTime;
}
