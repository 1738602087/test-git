package com.lxy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lxy
 * @since 2020-10-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Customer对象", description="")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户编号(主键)")
    @TableId(value = "cust_id", type = IdType.AUTO)
    private String custId;

    @ApiModelProperty(value = "客户名称(公司名称)")
    private String custName;

    @ApiModelProperty(value = "负责人id")
    private String custUserId;

    @ApiModelProperty(value = "创建人id")
    private String custCreateId;

    @ApiModelProperty(value = "客户信息来源")
    private String custSource;

    @ApiModelProperty(value = "客户所属行业")
    private String custIndustry;

    @ApiModelProperty(value = "客户级别")
    private String custLevel;

    @ApiModelProperty(value = "联系人")
    private String custLinkman;

    @ApiModelProperty(value = "固定电话")
    private String custPhone;

    @ApiModelProperty(value = "移动电话")
    private String custMobile;

    private String custZipcode;

    private String custAddress;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime custCreatetime;


}
