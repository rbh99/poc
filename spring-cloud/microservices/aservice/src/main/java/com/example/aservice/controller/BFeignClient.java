package com.example.aservice.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * Feign Client to Bservice
 *
 */
@FeignClient(value = "bservice", path = "/bservice", fallback = HystrixClientFallback.class)
public interface BFeignClient {

	@RequestMapping(method = RequestMethod.GET, value = "/bstatus/{name}")
	List<String> getBserviceResults(@PathVariable("name") String name);
}
