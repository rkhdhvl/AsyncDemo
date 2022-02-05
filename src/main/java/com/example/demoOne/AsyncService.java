package com.example.demoOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

	@Autowired
	private RestTemplate restTemplate;

	@Async
	public CompletableFuture<Object> callMsgService() {
		final String msgServiceUrl = "https://dummy.restapiexample.com/api/v1/employee/1";

		final Object response = restTemplate.getForObject(msgServiceUrl, Object.class);

		return CompletableFuture.completedFuture(response);
	}



}
