package com.example.aservice.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class HystrixClientFallback implements BFeignClient {
	   
		@Override
		public List<String> getBserviceResults(String name) {
			return Collections.singletonList("fallback on HystrixClientFallback bservice ");
		}

}
