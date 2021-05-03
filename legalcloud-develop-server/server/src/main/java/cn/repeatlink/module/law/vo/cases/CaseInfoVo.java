/**
 * 
 */
package cn.repeatlink.module.law.vo.cases;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LAI
 * @date 2020-10-09 16:49
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("案件信息")
public class CaseInfoVo extends CaseItemVo {

	@ApiModelProperty("买主个人情报")
	private List<CaseBuyerInfo> buyers;
	@ApiModelProperty("不动产情报")
	private List<CaseEstateInfo> estate_list;
}
