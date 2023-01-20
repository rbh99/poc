package com.example.appgateway;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

import lombok.extern.slf4j.Slf4j;

@EnableFeignClients
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
@Slf4j
public class AppgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppgatewayApplication.class, args);
	}

	
	@RestController
	class ServiceInstanceRestController {

	  @Autowired
	  private EurekaClient eurekaClient;

	  @RequestMapping("/service-instances/{applicationName}")
	  public List<InstanceInfo> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		  
		  
	    return this.eurekaClient.getInstancesById(applicationName);
	  }
	}
}
