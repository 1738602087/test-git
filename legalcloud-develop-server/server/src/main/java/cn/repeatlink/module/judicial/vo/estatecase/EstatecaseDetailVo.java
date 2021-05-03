/**
 * 
 */
package cn.repeatlink.module.judicial.vo.estatecase;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LAI
 * @date 2020-09-10 15:03
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("案件详细信息")
public class EstatecaseDetailVo extends EstatecaseBaseVo {
	
	@ApiModelProperty("买主集合")
	private List<EstatecaseBuyerVo> buyers;
	@ApiModelProperty("卖主集合")
	private List<EstatecaseSellerVo> sellers;

}
