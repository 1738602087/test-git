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
 * Spring ??????????????????
 * 
 * @author tlq
 *
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * ???????????????????????????
	 */
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Autowired
	private JudicialUserDetailServiceImpl judicialUserDetailService;
	
	@Autowired
	private GeneralUserDetailServiceImpl generalUserDetailService;

	/**
	 * ?????????????????????
	 */
	@Autowired
	private AuthenticationEntryPointImpl unauthorizedHandler;
	/** ????????????????????????**/
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	/**
	 * ???????????????
	 */
	@Autowired
	private LogoutSuccessHandlerImpl logoutSuccessHandler;
	
	@Autowired
	private AuthenticationEventPublisher authenticationEventPublisher;

	/**
	 * ?????? ?????????????????? AuthenticationManager
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
     * ??????????????????????????????
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //????????????????????????????????????
        config.addAllowedOrigin("*");
        //??????????????????cookie
        config.setAllowCredentials(true);
        //???????????????????????????
        config.addAllowedHeader("*");
        //????????????????????????????????????
        config.addAllowedMethod("*");
        config.setMaxAge(3600l);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
				// CRSF????????????????????????session
				.csrf().disable()
				.cors().and()
				// ?????????????????????
				// ????????????
				.authorizeRequests()
				.antMatchers("/buildinfo").anonymous()
				.antMatchers("/auth/login").anonymous()
				// ???????????????
				.antMatchers("/app/law/reg**").anonymous()
				.antMatchers("/app/law/reg/**").anonymous()
				// ??????????????????
				.antMatchers("/app/judicial/auth/**").anonymous()
				.antMatchers("/app/judicial/validatecode/**").anonymous()
				// ????????????APP
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
				// ???????????????????????????????????????????????????
				.anyRequest().authenticated()
				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
				
				.and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				// ??????token??????????????????session
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
	 * ???????????????????????????
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}
	
	/**
	 * ????????????APP???????????????????????????
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
	 * ????????????APP?????????????????????
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
	 * ??????????????????
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
		auth.authenticationEventPublisher(authenticationEventPublisher);
	}
}
