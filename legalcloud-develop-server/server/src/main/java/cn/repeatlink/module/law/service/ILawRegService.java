/**
 * 
 */
package cn.repeatlink.module.law.service;

import cn.repeatlink.module.law.vo.auth.LawApplyVo;
import cn.repeatlink.module.law.vo.auth.LawRegVo;

/**
 * @author LAI
 * @date 2021-01-14 13:35
 */
public interface ILawRegService {

	/**
	 * @param vo
	 */
	void apply(LawApplyVo vo);

	/**
	 * @param email
	 * @param regCode
	 */
	void checkRegCode(String email, String regCode);

	/**
	 * @param vo
	 */
	void regGroup(LawRegVo vo);

	/**
	 * @param vo
	 */
	void applyCheck(LawApplyVo vo);

}
