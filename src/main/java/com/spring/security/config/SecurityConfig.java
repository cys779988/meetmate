package com.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.spring.security.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private UserService userService;
	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/css/**", "/js/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests()
				// 페이지권한설정
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/chat/**").hasAnyRole("GUEST, ADMIN")
				.antMatchers("/board/**").hasAnyRole("GUEST, ADMIN")
				.antMatchers("/api/**").permitAll()
			.and()
				.formLogin()
				.loginPage("/user/login")
				.defaultSuccessUrl("/board/")
				.usernameParameter("username")
				.passwordParameter("password")
			.and()	// 로그아웃설정
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
				.logoutSuccessUrl("/user/login")
				.invalidateHttpSession(true)
			.and()
				.oauth2Login()
					.userInfoEndpoint()
						.userService(customOAuth2UserService);
		//http.csrf().ignoringRequestMatchers(new CsrfSecurityRequestMatcher())
		//			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * auth.inMemoryAuthentication() .withUser("test")
		 * .password(passwordEncoder().encode("test")) .roles("ADMIN");
		 */
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
}
