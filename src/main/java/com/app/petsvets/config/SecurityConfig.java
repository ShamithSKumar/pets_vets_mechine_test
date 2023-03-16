package com.app.petsvets.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.petsvets.serviceImpl.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Lazy
	@Autowired
	private JwtAuthFilter authFilter;

	@Bean
	public UserDetailsService userDetailService() {
		return new UserServiceImpl();
	}
	
	/**
	 * SecurityFilterChain created as as bean to match and 
	 * decide which request matcher should be allowed
	 * @param http
	 * @return securityFilterChain with details to filter and match requests
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers("/user/login", "/user/create","/user/login").permitAll()
				.and()
				.authorizeHttpRequests()
				.requestMatchers("/user/**","/pet/**","/userpet/**").authenticated()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authenticationProvider(authProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
				.logout()
				.logoutSuccessUrl("/user/logout")
				.invalidateHttpSession(true)
				.deleteCookies("JESSIONID")
				.and()
				.build();
	}
	
	@Bean
	public PasswordEncoder paswswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * To make sure the login user is valid and authenticated
	 * @return
	 */
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailService());
		authenticationProvider.setPasswordEncoder(paswswordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
