/**
 * 
 */
package cn.repeatlink.module.law.vo.dash;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LAI
 * @date 2021-01-20 17:14
 */

@Data
public class LawGroupItemVo {
	
	@ApiModelProperty("事務所名")
	private String groupName;
	
	@ApiModelProperty("代表者")
	private String representer;
	
	@ApiModelProperty("所在地")
	private String addr;
	
	@ApiModelProperty("主担当者")
	private String staff;
	
	@ApiModelProperty("メールアドレス")
	private String email;
	
	@ApiModelProperty("連絡先電話番号")
	private String tel;
	
	@ApiModelProperty("利用開始日付")
	private String createDate;

	@ApiModelProperty("ID")
	private String id;
	
	public String getId() {
		return IdUtil.fastSimpleUUID();
	}
}
