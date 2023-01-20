package com.example.appgateway.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class HystrixClientFallback implements AFeignClient {
   
	//@Override
	public List<String> getAserviceResults(String name) {
		return Collections.singletonList("fallback on HystrixClientFallback aservice ");
	}
}