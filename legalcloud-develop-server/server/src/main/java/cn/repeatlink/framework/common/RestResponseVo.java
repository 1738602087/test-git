package cn.repeatlink.framework.common;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 最终返回给前端的结果，兼容ASP V3协议
 * @author tlq20
 *
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RestResponseVo<T> extends AjaxResult<T> {
    

    private AjaxResult<T> results;

    public RestResponseVo() {
	super();
    }

    public RestResponseVo(AjaxResult<T> results) {
	super();
	this.results = results;
    }

    public AjaxResult<T> getResults() {
	return results;
    }

    public void setResults(AjaxResult<T> results) {
	this.results = results;
    }

}
