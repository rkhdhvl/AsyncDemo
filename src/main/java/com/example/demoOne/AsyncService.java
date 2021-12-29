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
	public CompletableFuture<String> callMsgService() {
		final String msgServiceUrl = "http://localhost:8081/msg";

		final String response = restTemplate.getForObject(msgServiceUrl, String.class);

		return CompletableFuture.completedFuture(response);
	}

}
