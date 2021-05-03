/**
 * 
 */
package cn.repeatlink.module.law.vo.dash;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import cn.repeatlink.module.law.vo.SeachVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LAI
 * @date 2021-01-19 14:28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DownloadSeachVo extends SeachVo {
	
	@ApiModelProperty("下载文件类型，csv/xls/xlsx")
	@JsonProperty("file_type")
	private String fileType;
	
	public String getFileType() {
		return StringUtils.isBlank(fileType) ? "csv" : fileType;
	}

}
