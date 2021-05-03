/**
 * 
 */
package cn.repeatlink.module.judicial.common;

/**
 * @author LAI
 * @date 2020-09-10 14:05
 */
public class Define {
	
	public static final String APP_URL_PREFIX = "/app/judicial";
	
	public static final String CACHE_NAME_TEMP = "temp_cache";
	public static final String CACHE_NAME_SYS_CONFIG = "sys_config";
	
	public static interface ConfigKeys {
		// AWS人脸识别
		String AWS_FACE_CONFIDENCE_MIN = "aws.face.confidence.min";
		String AWS_FACE_COLLECTION_ID = "aws.face.collection.id";
		
		// OMISE
		String OMISE_PUBLIC_KEY = "omise.public.key";
		String OMISE_SECRET_KEY = "omise.secret.key";
		
		// 人脸验证
		String FACE_VERIFY_IMG_STORE_PATH = "estate.user.face.verify.img.store.path";
		
		// 不动产fetch帐号密码
		String ESTATE_SPIDER_FETCH_LOGIN_USERNAME = "estate.spider.fetch.login.username";
		String ESTATE_SPIDER_FETCH_LOGIN_PASSWORD = "estate.spider.fetch.login.password";
		// 不动产fetch下载pdf储存目录
		String ESTATE_SPIDER_FETCH_PDF_STORE_PATH = "estate.spider.fetch.pdf.store.path";
		// 不动产fetch开关
		String ESTATE_SPIDER_FETCH_SWITCH = "estate.spider.fetch.switch";
	}
	
	public static interface TempCacheKeys {
		String USER_FACE_2_CODE = "user_face_2_code";
	}

}
