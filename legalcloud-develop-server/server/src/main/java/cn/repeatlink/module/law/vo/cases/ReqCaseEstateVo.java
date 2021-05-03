/**
 * 
 */
package cn.repeatlink.module.law.vo.cases;

import java.util.List;

import cn.repeatlink.module.judicial.vo.estate.EstateBaseVo;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-02-22 13:24
 */

@Data
public class ReqCaseEstateVo {

	private List<EstateBaseVo> estate_list;
	
}
