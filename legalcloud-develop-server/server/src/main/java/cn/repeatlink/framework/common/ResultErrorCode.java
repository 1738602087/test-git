package cn.repeatlink.framework.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import cn.repeatlink.framework.util.MessageUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("错误提示信息")
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResultErrorCode {
	@ApiModelProperty("错误码")
	private String code;
	@ApiModelProperty("资源文件中对应的key")
	private String fieldname;
	@ApiModelProperty("提示信息")
	private String msg;
	public ResultErrorCode() {
		
	}
	public ResultErrorCode(String code, String fieldname, String msg) {
		super();
		this.code = code;
		this.fieldname = fieldname;
		this.msg = msg;
	}
	public ResultErrorCode(String code, String fieldname) {
		super();
		this.code = code;
		this.fieldname = fieldname;
		this.msg = MessageUtil.getMessage(fieldname);
	}
	
	public ResultErrorCode(String code, String fieldname, String ...args) {
		super();
		this.code = code;
		this.fieldname = fieldname;
		this.msg = MessageUtil.getMessage(fieldname,args);
	}
}
