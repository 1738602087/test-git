package cn.repeatlink.framework.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;

import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.ds.GlobalDSFactory;
import cn.repeatlink.framework.cache.CacheKit;
import lombok.Setter;

/**
 * 用于通用bean的配置
 * 
 * @author tlq20
 *
 */
@Configuration
public class BeanConfig {
	@Setter
	@Value("${spring.i18n.basename}")
	private String[] basename;

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames(basename);
		source.setUseCodeAsDefaultMessage(true);
		source.setDefaultEncoding("UTF-8");
		return source;
	}

	@Bean
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	/**
	 * 集成JFinal数据库操作，可进行简单的SQL查询（Spring事务不支持此处DB操作，内部可实现）
	 * @param dataSource
	 * @return
	 */
	@Bean
	public ActiveRecordPlugin activeRecordPlugin(DataSource dataSource) {
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dataSource);
		arp.start();
		return arp;
	}
	
	/**
	 * 集成hutool数据库操作，可进行简单的SQL查询（Spring事务不支持此处DB操作，内部可实现）
	 * @param dataSource
	 * @return
	 */
	@Bean
	public DSFactory dsFactory(DataSource dataSource) {
		// 使用既有数据源对象创建数据源工厂
		DSFactory dsFactory = new DSFactory(null) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public DataSource getDataSource(String group) {
				return dataSource;
			}
			
			@Override
			public void destroy() {
				// 交给Spring
				System.out.println("BeanConfig.dsFactory(...).new DSFactory() {...}.destroy()");
			}
			
			@Override
			public void close(String group) {
				// 交给Spring
				System.out.println("BeanConfig.dsFactory(...).new DSFactory() {...}.close()");
			}
		};
		GlobalDSFactory.set(dsFactory);
		return dsFactory;
	}
	
	@Bean
	public EhCachePlugin ehCachePlugin() {
		EhCachePlugin ehCachePlugin = new EhCachePlugin(((EhCacheCacheManager)CacheKit.cacheManager()).getCacheManager());
		ehCachePlugin.start();
		return ehCachePlugin;
	}

}
