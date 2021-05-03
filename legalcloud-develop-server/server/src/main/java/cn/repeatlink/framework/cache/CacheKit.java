/**
 * 
 */
package cn.repeatlink.framework.cache;

import org.springframework.cache.CacheManager;

import cn.repeatlink.framework.util.SpringBeanFactory;

/**
 * @author LAI
 * @date 2020-09-03 14:33
 */
public class CacheKit {
	
	private static CacheManager cacheManager = SpringBeanFactory.getBean(CacheManager.class);
	
	@SuppressWarnings("unchecked")
	public static <T> T getVal(String cacheName, Object cacheKey) {
		return (T)cacheManager.getCache(cacheName).get(cacheKey).get();
	}
	
	public static void put(String cacheName, Object cacheKey, Object cacheVal) {
		cacheManager.getCache(cacheName).put(cacheKey, cacheVal);
	}
	
	public static void clear(String cacheName) {
		cacheManager.getCache(cacheName).clear();
	}
	
	public static RLCache use(String cacheName) {
		return new RLCache(cacheManager.getCache(cacheName));
	}

	public static CacheManager cacheManager() {
		return cacheManager;
	}
}
