package com.amstech.std.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeanConfig {

	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
	 
}
