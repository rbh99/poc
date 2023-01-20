package com.example.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer //it will enable a Spring Security filter that authenticates requests via an incoming OAuth2 token.
@EnableDiscoveryClient //it will enable the discovery client implementation to let our auth service register in Registry Service
@EnableGlobalMethodSecurity(prePostEnabled = true) // enables Spring Security global method security.
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
