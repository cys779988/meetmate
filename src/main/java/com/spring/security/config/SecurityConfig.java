package com.spring.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.spring.security.service.CustomOAuth2UserService;
import com.spring.security.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private UserService userService;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final AuthenticationSuccessHandler customSuccessHandler;
	private final AuthenticationFailureHandler customFailureHandler;
	private final LogoutHandler logoutHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new CustomAuthenticationProvider(userService);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/assets/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// 페이지권한설정
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/user/**").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/user/login")
				.loginProcessingUrl("/user/loginProc")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/board/")
				.successHandler(customSuccessHandler)
				.failureHandler(customFailureHandler)
				.permitAll()
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
				.logoutSuccessUrl("/user/login")
				.addLogoutHandler(logoutHandler)
				.invalidateHttpSession(true)
			.and()
				.oauth2Login()
					.userInfoEndpoint()
						.userService(customOAuth2UserService);
		//http.csrf().ignoringRequestMatchers(new CsrfSecurityRequestMatcher())
		//			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		//auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	private AccessDeniedHandler accessDeniedHandler() {
		CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
		accessDeniedHandler.setErrorPage("/denied");
		return accessDeniedHandler;
	}
}
