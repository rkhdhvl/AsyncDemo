package com.example.demoOne;

import com.example.demoOne.error_handling.ResponseEntityErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntityErrorHandler errorHandler = new ResponseEntityErrorHandler();
		errorHandler.setMessageConverters(restTemplate.getMessageConverters());
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate;
	}

}


