package cn.repeatlink.module.law.vo.cases;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Lsq
 * @date 2020-12-14 15:36
 */
@Data
@Api("案件信息获取")
public class CaseInfo {

    @ApiModelProperty("唯一ID")
    private String id;

    @ApiModelProperty("案件名")
    private String caseName;

    @ApiModelProperty("开始日")
    private String startDate;

    @ApiModelProperty("担当者")
    private String judName;

    @ApiModelProperty("取引物件数")
    private String num;

    @ApiModelProperty("取引物件")
    private String addr;

    @ApiModelProperty("卖主验证")
    private String stepSellerVerify;

    @ApiModelProperty("买主登录")
    private String stepBuyerInput;

    @ApiModelProperty("取引完了")
    private String stepRegFinish;

    @ApiModelProperty("登记完了")
    private String stepDealFinish;

    @ApiModelProperty("终了日")
    private String overTime;

    @JsonIgnore
    private String status;
}
