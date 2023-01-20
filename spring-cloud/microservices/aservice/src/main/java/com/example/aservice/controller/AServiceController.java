package com.example.aservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;

import lombok.extern.slf4j.Slf4j;

@RefreshScope
@RestController
@RequestMapping("/aservice")
@Slf4j
public class AServiceController {
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Value("${spring.application.name}")
    private String appName;
	
	@Value("${aservice.myproperty: not found}")
	private String myproperty;
	
	@Value("${prop1}")
	private String prop1;
	
	@Autowired
	private BFeignClient bFeignClient;
	
	
	@GetMapping(value="/astatus/{name}")
	public List<String> getServiceAData(@PathVariable("name") String name) {
		
		log.debug("calling getServiceAData with {}", name);
		
		//return " A service was called with "+ name + " and will Upper case the response from B >> "+StringUtils.upperCase(name) +"<< ";
		List<String> list = new ArrayList<>();
		
		list.add("myproperty is "+myproperty + " and prop1 "+prop1 );
		
		list.add(("aservice called on " + eurekaClient.getApplication(appName).getName()+" on instance "+ eurekaClient.getInstanceRemoteStatus()));
		
		list.add(" calling B Service with name " + name);
		
		list.addAll(bFeignClient.getBserviceResults(name));
		
        
        return list;		
		
	}
	

}
