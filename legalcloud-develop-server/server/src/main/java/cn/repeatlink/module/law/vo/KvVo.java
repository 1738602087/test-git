/**
 * 
 */
package cn.repeatlink.module.law.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author LAI
 * @date 2020-10-10 10:44
 */
@Data
@ApiModel("键值数据")
public class KvVo {
	
	private String key;
	
	private String value;

}
