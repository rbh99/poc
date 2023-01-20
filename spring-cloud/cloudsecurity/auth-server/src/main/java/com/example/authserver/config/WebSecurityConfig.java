package com.example.authserver.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * 
 * adds end users
 * 
 * The SecurityConfiguration class is the class that actually authenticates requests to your authorization server. 
 * 
 * Notice near the top where it’s pulling in the username and password from the application.properties file.
 *
 */

@Configuration
@Order(1) //higher priority
@EnableWebSecurity // not needed because of @EnableAuthorizationServer
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	 @Value("${user.oauth.user.username}")
	 private String username;
	 
	 @Value("${user.oauth.user.password}")
	 private String password;
	
	private final PasswordEncoder passwordEncoder;

	//expose an authentication manager
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/** 
	 * configure http security
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.requestMatchers().antMatchers("/login", "/oauth/authorize")
		.and()
		.formLogin().permitAll()
		.and()
		.authorizeRequests().antMatchers("/**").authenticated()
		
		.and()
		.exceptionHandling().authenticationEntryPoint((request, response, authException) -> 
			{
				log.info("for the request {} when authenticating entry point we get the exception {}", request, authException);
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			})
		;
	}

	/**
	 * configure the authentication manager declared in the bean def.
	 * 
	 * used to build the authentication manager, any exposed authentication manager is picked by oauth
	 * 
	 * needed Only when using the Resource Owner Password Flow, meaning when grant type includes 'password'
	 * .authorizedGrantTypes("password", ...)
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser(username)
	        .password(passwordEncoder.encode(password))
	        .roles("USER")
        .and()
			.withUser("reader")
			.password(passwordEncoder.encode ("pass"))
			.authorities("FOO_READ")
		.and()
			.withUser("writer")
			.password(passwordEncoder.encode ("pass"))
			.authorities("FOO_READ", "FOO_WRITE");
	}
	
	/**
	 * build user details, their roles
	 */
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails reader = User.builder().username("reader").password(passwordEncoder.encode("pass"))
				.roles("FOO_READ").build();

		UserDetails user = User.builder().username(username).password(passwordEncoder.encode(password)).roles("USER")
				.build();

		UserDetails writer = User.builder().username("writer").password(passwordEncoder.encode("pass"))
				.roles("FOO_READ", "FOO_WRITE").build();

		return new InMemoryUserDetailsManager(user, reader, writer);
	}

}
