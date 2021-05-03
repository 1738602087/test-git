/**
 * 
 */
package cn.repeatlink.module.judicial.util;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import cn.repeatlink.module.judicial.vo.user.FaceUserVo;

/**
 * @author LAI
 * @date 2020-11-03 15:18
 */
public class FaceCodeStore {
	
	static final Cache<String, FaceUserVo> cache = CacheBuilder.newBuilder().initialCapacity(1000).concurrencyLevel(10)
			.expireAfterWrite(5, TimeUnit.MINUTES).build();

	public static void put(String key, FaceUserVo value) {
		cache.put(key, value);
	}

	public static FaceUserVo get(String key)  {
			return cache.getIfPresent(key);
	}
	
	public static void delete(String key)  {
		cache.invalidate(key);
	}

}
