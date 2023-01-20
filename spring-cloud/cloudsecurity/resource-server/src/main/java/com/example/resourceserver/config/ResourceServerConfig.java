package com.example.resourceserver.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * This class defines the Spring Security configuration for your application: 
 * allowing all requests on the home path and requiring authentication for all other routes. 
 * It also sets up the Spring Boot OAuth login flow.
 *
 */

@Configuration
@EnableResourceServer //Adding this annotation adds the OAuth2AuthenticationProcessingFilter, though it will need one more configuration to know how to appropriately process and validate tokens.
@Slf4j
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/**").authenticated()
			.antMatchers(HttpMethod.GET, "/foo").hasAuthority("FOO_READ")
			//.access("#oauth2.hasScope('foo1scope')")
		
			;  

	}

	/*@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
	
	/**
	 * Internally, Resource Server uses an OAuth2RestTemplate to invoke the /userinfo endpoint. 
	 * At times, it may be necessary to add filters or perform other customization for this invocation. 
	 * To customize the creation of this bean, you can expose a UserInfoRestTemplateCustomizer, like so:
	 */
	@Bean
	public UserInfoRestTemplateCustomizer customHeader() {
		return restTemplate ->
				restTemplate.getInterceptors().add((HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
					log.debug("intercepting request {} eith body ",request, body);
					return execution.execute(request, body);
				});
	}
}
