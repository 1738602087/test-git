package cn.repeatlink.module.law.vo.dash;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lsq
 * @date 2020-12-15 14:50
 */
@Data
@Api
public class SaleRecordInfo {

    @ApiModelProperty("唯一ID")
    private String id;

    @ApiModelProperty("顧客名")
    private String fullname;
    
    @ApiModelProperty("所属事務所")
    private String groupName;

    @ApiModelProperty("利用サビース名")
    private String desc;

    @ApiModelProperty("決済金額")
    private String amount;

    @ApiModelProperty("決済日")
    private String deductionDate;

    @ApiModelProperty("決済カード")
    private String cardNo;

    @ApiModelProperty("カード発行会社")
    private String creditCompanyName;

    @ApiModelProperty("決済状態")
    private String status;

    @ApiModelProperty("利用開始日")
    private String startDate;

}
