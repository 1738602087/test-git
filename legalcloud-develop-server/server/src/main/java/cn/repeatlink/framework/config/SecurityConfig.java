package cn.repeatlink.framework.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import cn.repeatlink.framework.config.ss.AuthenticationEntryPointImpl;
import cn.repeatlink.framework.config.ss.JwtAuthenticationTokenFilter;
import cn.repeatlink.framework.config.ss.JwtAuthorizationTokenFilter;
import cn.repeatlink.framework.config.ss.JwtUserGeneralAuthenticationTokenFilter;
import cn.repeatlink.framework.config.ss.JwtUserJudicialAuthenticationTokenFilter;
import cn.repeatlink.framework.config.ss.LogoutSuccessHandlerImpl;
import cn.repeatlink.framework.config.ss.UserGeneralAuthenticationProvider;
import cn.repeatlink.framework.config.ss.UserJudicialAuthenticationProvider;
import cn.repeatlink.framework.service.impl.GeneralUserDetailServiceImpl;
import cn.repeatlink.framework.service.impl.JudicialUserDetailServiceImpl;
import cn.repeatlink.framework.service.impl.UserDetailServiceImpl;

/**
 * Spring 权限相关拦截
 * 
 * @author tlq
 *
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 自定义用户认证逻辑
	 */
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Autowired
	private JudicialUserDetailServiceImpl judicialUserDetailService;
	
	@Autowired
	private GeneralUserDetailServiceImpl generalUserDetailService;

	/**
	 * 认证失败处理类
	 */
	@Autowired
	private AuthenticationEntryPointImpl unauthorizedHandler;
	/** 权限不足时处理类**/
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	/**
	 * 退出处理类
	 */
	@Autowired
	private LogoutSuccessHandlerImpl logoutSuccessHandler;
	
	@Autowired
	private AuthenticationEventPublisher authenticationEventPublisher;

	/**
	 * 解决 无法直接注入 AuthenticationManager
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
    /**
     * 允许跨域调用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //允许所有域名进行跨域调用
        config.addAllowedOrigin("*");
        //允许跨越发送cookie
        config.setAllowCredentials(true);
        //放行全部原始头信息
        config.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        config.addAllowedMethod("*");
        config.setMaxAge(3600l);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
				// CRSF禁用，因为不使用session
				.csrf().disable()
				.cors().and()
				// 认证失败处理类
				// 过滤请求
				.authorizeRequests()
				.antMatchers("/buildinfo").anonymous()
				.antMatchers("/auth/login").anonymous()
				// 事务所注册
				.antMatchers("/app/law/reg**").anonymous()
				.antMatchers("/app/law/reg/**").anonymous()
				// 司法书士注册
				.antMatchers("/app/judicial/auth/**").anonymous()
				.antMatchers("/app/judicial/validatecode/**").anonymous()
				// 一般用户APP
				.antMatchers("/app/general/auth/**").anonymous()
				.antMatchers("/app/general/validatecode/**").anonymous()
				.antMatchers("/app/general/reg/**").anonymous()
//				.antMatchers("/user/*").hasAnyRole("admin")
				.antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
				.antMatchers("/profile/**").permitAll().antMatchers("/common/download**").permitAll()
				.antMatchers("/common/download/resource**").permitAll().antMatchers("/swagger-ui.html").permitAll()
				.antMatchers("/swagger-resources/**").permitAll().antMatchers("/webjars/**").permitAll()
				.antMatchers("/*/api-docs").permitAll().antMatchers("/*/api-docs-ext").permitAll()
				.antMatchers("/druid/**").permitAll()
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated()
				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
				
				.and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				// 基于token，所以不需要session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				
				.headers().frameOptions().disable().and().logout()
				.logoutUrl("/auth/logout").logoutSuccessHandler(logoutSuccessHandler);

		httpSecurity
				.addFilterBefore(new JwtAuthenticationTokenFilter(authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(new JwtUserJudicialAuthenticationTokenFilter(userJudicialProviderManager()), JwtAuthenticationTokenFilter.class)
				.addFilter(new JwtAuthorizationTokenFilter(authenticationManager()))
				.addFilter(new JwtUserGeneralAuthenticationTokenFilter(userGeneralProviderManager()))
		;

	}

	/**
	 * 强散列哈希加密实现
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}
	
	/**
	 * 司法书士APP用户登录认证管理器
	 * @return
	 */
	private ProviderManager userJudicialProviderManager() {
		DaoAuthenticationProvider provider = new UserJudicialAuthenticationProvider();
		provider.setUserDetailsService(judicialUserDetailService);
		provider.setUserDetailsPasswordService(judicialUserDetailService);
		ProviderManager providerManager = new ProviderManager(Arrays.asList(provider));
		providerManager.setAuthenticationEventPublisher(authenticationEventPublisher);
		return providerManager;
	}
	
	/**
	 * 一般用户APP登录认证管理器
	 * @return
	 */
	private ProviderManager userGeneralProviderManager() {
		DaoAuthenticationProvider provider = new UserGeneralAuthenticationProvider();
		provider.setUserDetailsService(generalUserDetailService);
		provider.setUserDetailsPasswordService(generalUserDetailService);
		ProviderManager providerManager = new ProviderManager(Arrays.asList(provider));
		providerManager.setAuthenticationEventPublisher(authenticationEventPublisher);
		return providerManager;
	}

	/**
	 * 身份认证接口
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
		auth.authenticationEventPublisher(authenticationEventPublisher);
	}
}
