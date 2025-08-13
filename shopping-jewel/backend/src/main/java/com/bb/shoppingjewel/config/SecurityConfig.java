package com.bb.shoppingjewel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bb.shoppingjewel.security.JwtFilter;

@Configuration
public class SecurityConfig {
	private final JwtFilter jwtFilter;
	
	public SecurityConfig(JwtFilter jwtFilter){ this.jwtFilter = jwtFilter; }

	//@Bean
	public SecurityFilterChain filterChain_NotInUse(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/api/auth/**", "/api/products/**", "/h2-console/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		http.headers().frameOptions().disable();
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.cors().and()
		.authorizeHttpRequests()
		.requestMatchers(
				"/api/auth/login",
				"/api/auth/register",
				"/api/products/**",
				"/h2-console/**",
				"/actuator/health"
				).permitAll()
		.anyRequest().authenticated()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.headers().frameOptions().disable();

		// Add JWT filter but after login is permitted
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}

