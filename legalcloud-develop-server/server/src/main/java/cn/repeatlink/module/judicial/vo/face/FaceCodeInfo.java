/**
 * 
 */
package cn.repeatlink.module.judicial.vo.face;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jfinal.kit.Kv;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LAI
 * @date 2020-09-23 15:04
 */

@Data
@Accessors(chain = true)
public class FaceCodeInfo {

	private String face_code;
	
	private String use_type;
	
	@JsonIgnore
	private String base64data;
	
	@JsonIgnore
	private Kv attrs;
	
}
