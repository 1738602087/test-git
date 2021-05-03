/**
 * 
 */
package cn.repeatlink.framework.cache;

import org.springframework.cache.Cache;

/**
 * @author LAI
 * @date 2020-09-03 14:46
 */
public class RLCache {
	
	private Cache cache;
	
	public RLCache(Cache cache) {
		this.cache = cache;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Object key) {
		if(this.cache.get(key) == null) return null;
		return (T)this.cache.get(key).get();
	}
	
	public <T> T get(Object key, Class<T> type) {
		return this.cache.get(key, type);
	}
	
	public void put(Object key, Object value) {
		this.cache.put(key, value);
	}
	
	public void clear() {
		this.cache.clear();
	}
	
	public void evict(Object key) {
		this.cache.evict(key);
	}
	
	public boolean isNullCache() {
		return this.cache == null;
	}
	
	public boolean contains(Object key) {
		return this.cache.get(key) != null;
	}

}
