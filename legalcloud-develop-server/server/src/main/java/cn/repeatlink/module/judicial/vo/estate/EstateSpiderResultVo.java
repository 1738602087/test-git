/**
 * 
 */
package cn.repeatlink.module.judicial.vo.estate;

import java.io.File;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LAI
 * @date 2020-12-30 10:39
 */

@Data
@Accessors(chain = true)
public class EstateSpiderResultVo {

	// private String addr;
	
	// private String houseId;
	
	private String struct;
	
	private String area;
	
	private String category;
	
	private String estateNo;
	
	private File pdfFile;
	
	private String record;
	
	private String errorMsg;
	
	private String holder;
	
}
