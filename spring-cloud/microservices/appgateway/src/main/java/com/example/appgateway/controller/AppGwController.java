package com.example.appgateway.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AppGwController {
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Value("${spring.application.name}")
    private String appName;
	
	@Autowired
	private AFeignClient aFeignClient;
	
	@GetMapping(value ="/app/{name}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public List<String> greeting(@PathVariable("name") String name) {
		
		log.debug(" get request with parameter {}", name);
		//eurekaClient.getInstanceRemoteStatus()
		List<String> list = new ArrayList<>();
		//list.add(("app gw controller called with name = " + name));
		list.add("appgateway /app called on " + eurekaClient.getApplication(appName).getName()+" on instance "+ eurekaClient.getApplicationInfoManager().getInfo());
		
		list.add(" calling A Service with name " + name);
		
		List<String> fromA = aFeignClient.getAserviceResults(name);
		log.debug(" we calld a service and we got {}", fromA);
		list.addAll(fromA);
		
        
        return list;		
    }
	

}
