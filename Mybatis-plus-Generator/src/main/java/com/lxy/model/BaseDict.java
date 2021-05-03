package com.lxy.model;

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
@ApiModel(value="BaseDict对象", description="")
public class BaseDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据字典id(主键)")
    private String dictId;

    @ApiModelProperty(value = "数据字典类别代码")
    private String dictTypeCode;

    @ApiModelProperty(value = "数据字典类别名称")
    private String dictTypeName;

    @ApiModelProperty(value = "数据字典项目名称")
    private String dictItemName;

    @ApiModelProperty(value = "数据字典项目代码(可为空)")
    private String dictItemCode;

    @ApiModelProperty(value = "排序字段")
    private String dictSort;

    @ApiModelProperty(value = "1:使用 0:停用")
    private String dictEnable;

    @ApiModelProperty(value = "备注")
    private String dictMemo;


}
