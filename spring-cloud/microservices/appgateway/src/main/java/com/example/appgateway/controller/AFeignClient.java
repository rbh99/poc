package com.example.appgateway.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.Resources;



/**
 * 
 *feign client to aservice
 *
 */
@FeignClient(value = "aservice", path = "/aservice" ,  fallback = HystrixClientFallback.class)
public interface AFeignClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/astatus/{name}")
    List<String> getAserviceResults(@PathVariable("name") String name);
	//Resources<String> getAserviceResults(String name);
	

}
