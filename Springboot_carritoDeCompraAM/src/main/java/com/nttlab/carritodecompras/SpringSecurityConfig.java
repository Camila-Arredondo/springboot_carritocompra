package com.nttlab.carritodecompras;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.nttlab.carritodecompras.auth.handler.LoginSuccessHandler;
import com.nttlab.carritodecompras.models.service.JpaUserDetailService;


@Configuration
public class SpringSecurityConfig {
	
	@Autowired
	private LoginSuccessHandler logiSuccessHandler;
	
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private JpaUserDetailService userDetailService;
	
	@Autowired
	public void userDetailService(AuthenticationManagerBuilder build) throws Exception {
		build.userDetailsService(userDetailService)
		.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests()
		.requestMatchers("/**","/css/**","/js/**","/img/**","/api/**").permitAll()
			.requestMatchers("/registrar/**").permitAll()
			.requestMatchers("/api/**").permitAll()
;

		return http.build();

	}
	
}
