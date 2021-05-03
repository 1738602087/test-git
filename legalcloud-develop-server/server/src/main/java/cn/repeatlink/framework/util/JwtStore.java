package cn.repeatlink.framework.util;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import cn.repeatlink.framework.bean.LoginUser;

@Component
public class JwtStore{
	static final Cache<String, LoginUser> cache = CacheBuilder.newBuilder().initialCapacity(1000).concurrencyLevel(10)
			.expireAfterAccess(JwtUtils.EXPIRITION, TimeUnit.MILLISECONDS).build();

	public static void put(String key, LoginUser value) {
		cache.put(key, value);
	}

	public static LoginUser get(String key)  {
		
			return cache.getIfPresent(key);
		
	}
	public static void delete(String key)  {
		cache.invalidate(key);
	}
}
