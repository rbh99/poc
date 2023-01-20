package com.example.bservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BserviceApplication.class, args);
	}

}
