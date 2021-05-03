/**
 * 
 */
package cn.repeatlink.common.bean;

import java.io.InputStream;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LAI
 * @date 2020-08-25 14:47
 */

@Data
@Accessors(chain = true)
public class DownloadFileInfo {
	
	private InputStream is;
	
	private String fileName;
	
	private String md5;

}
