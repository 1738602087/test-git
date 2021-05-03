/**
 * 
 */
package cn.repeatlink.module.judicial.common;

/**
 * @author LAI
 * @date 2020-09-10 14:05
 */
public class Constant {
	
	public static interface CaseStatus {
		/**
		 * 0：无效
		 */
		Short INVALID = 0;
		
		/**
		 * 1：有效
		 */
		Short VALID = 1;
		
	}
	
	public static interface CaseSellerVerify {
		/**
		 * 0：未通过验证
		 */
		Short WAITING_VERIFY = 0;
		/**
		 * 1：已验证
		 */
		Short VERIFYED = 1;
	}
	
	public static interface CaseBuyerFaceStatus {
		/**
		 * 0：未登录
		 */
		Short NOT_INPUT = 0;
		/**
		 * 1：已登录
		 */
		Short INPUTED = 1;
	}
	
	public static interface UserFaceStatus {
		
		/**
		 * 1：有效的
		 */
		Short VALID = 1;
		/**
		 * 2：请求删除
		 */
		Short REQ_DELETE = 2;
		/**
		 * 3：已删除
		 */
		Short DELETED = 3;
	}
	
	public static interface CaseRecordType {
		/**
		 * 1：卖主
		 */
		Short SELLER = 1;
		/**
		 * 2：买主
		 */
		Short BUYER = 2;
	}
	
	public static interface CaseStepVal {
		/**
		 * -1：不需要
		 */
		Short NO = -1;
		/**
		 * 0：默认（未完成）
		 */
		Short DEFAULT = 0;
		/**
		 * 1：已完成
		 */
		Short COMPLETED = 1;
		/**
		 * 21：存在失败元素
		 */
		Short INCLUDE_ERROR = 22;
	}
	
	public static interface OmRecordType {
		/**
		 * 1：扣款
		 */
		Short DEDUCT = 1;
		/**
		 * 2：退款
		 */
		Short REFUND = 2;
	}

	public static interface CaseOperaAuth {
		/**
		 * 0：不可编辑
		 */
		Short NO_UPDATE = 0;
		/**
		 * 1：可编辑
		 */
		Short UPDATEABLE = 1;
	}
	
	public static interface ProtectOrderPayTag {
		/**
		 * 0：未支付
		 */
		Short NOT_PAY = 0;
		/**
		 * 1：支付成功
		 */
		Short PAYED = 1;
		/**
		 * 2：支付失败
		 */
		Short PAY_FAIL = 2;
	}
	
	public static interface EstateFetchStatus {
		/**
		 * 0：未开始
		 */
		Short NOT_FETCH = 0;
		/**
		 * 1：成功
		 */
		Short FETCH_OK = 1;
		/**
		 * 2：失败
		 */
		Short FETCH_ERROR = 2;
	}
}
