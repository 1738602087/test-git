/**
 * 
 */
package cn.repeatlink.module.general.vo.help;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LAI
 * @date 2021-02-02 11:40
 */

@Data
@Accessors(chain = true)
public class HelpInfo {
	@ApiModelProperty("司法書士法人")
	private String legal_name;
	@ApiModelProperty("ヘルプセンター連絡先")
	private String contact_tel;

}
