package com.shaance.catmashinterview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsGlobalConfiguration implements WebFluxConfigurer {

	@Value("${config.cross-origin.urls}")
	private String allowedCors;

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/**")
				.allowedOrigins(allowedCors)
				.allowedMethods("GET", "POST")
				.allowedHeaders("*")
				.maxAge(3600);
	}

}
