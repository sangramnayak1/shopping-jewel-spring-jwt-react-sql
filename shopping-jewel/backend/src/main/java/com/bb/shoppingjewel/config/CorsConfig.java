package com.bb.shoppingjewel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
				.allowedOriginPatterns(
						"http://localhost:*",         // local dev any port
						"http://127.0.0.1:*",         // loopback any port
						"http://shopping-frontend:*", // frontend container with port
						"http://shopping-frontend",   // frontend container by name
						"http://backend:*",           // backend container name with port
						"http://backend"              // backend container by name
						)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("*");
				//.allowCredentials(true); // remove this when check login through CURL
			}
		};
	}

}
