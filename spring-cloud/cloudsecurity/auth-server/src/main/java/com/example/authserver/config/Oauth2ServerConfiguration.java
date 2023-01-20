package com.example.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


/**
 * 
 * 
 * An AuthenticationManager is an abstraction for authenticating users. It typically needs some kind of UserDetailsService to be specified in order to be complete.
 * End users are specified in a WebSecurityConfigurerAdapter.
 * OAuth2 Boot, by default, automatically picks up any exposed AuthenticationManager.
 * 
 * 
 * 
 * 
 * 
 * 
 * exposing this disables spring boot defaults and configuration has to be done manually
 * 
 * the class that will create and return our JSON web tokens when the client properly authenticates.
 *
 */
@Configuration
@EnableAuthorizationServer //activate the authorization server.
public class Oauth2ServerConfiguration extends AuthorizationServerConfigurerAdapter {

	
	    @Value("${user.oauth.client-id}")
	    private String clientID;
	    @Value("${user.oauth.client-secret}")
	    private String clientSecret;
	    @Value("${user.oauth.redirect-uris}")
	    private String redirectURLs;
	
	/** for looking up users, not clients **/
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	/** for generating tokens **/
	@Autowired
	private TokenStore tokenStore;

	//the credentials of the administrator of authentications, and the services offered.
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// define clients. they are no longer retrieved from configuration file
		
		// @formatter:off
		
		clients.inMemory()
			.withClient(clientID) //is the user with whom we will identify
				.secret(passwordEncoder().encode(clientSecret)) //is the password of the client
				.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit") //we specify  services that configure for the defined user 
				.authorities("FOO_READ", "FOO_WRITE") //specifies roles or groups contained by the service offered.
				.scopes("foo1scope", "foo2scope") //is the scope of the service. so we can check this with #oauth2.hasScope(‘FOO’) to apply client access permission
				.autoApprove(true) //auto approve for the scope for code authorization and pass the authorities for resource server. if you must automatically approve the client's requests
				.redirectUris(redirectURLs) //redirects the user-agent to the client’s redirection endpoint. It must be an absolute URL.
				//.accessTokenValiditySeconds(5000)
				//.refreshTokenValiditySeconds(50000)
				/*	.and()
		.withClient(clientID)
			.secret(passwordEncoder().encode(clientSecret))
		    .authorizedGrantTypes("authorization_code")
		    .scopes("user_info")
		    .autoApprove(true)
		    .redirectUris(redirectURLs);*/
				
				
		// @formatter:on		
				;
	}
	
	//where we define which authentication controller and store of identifiers should use the end points
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.authenticationManager(authenticationManager)
			.tokenStore(tokenStore)
			
			 .exceptionTranslator(loggingExceptionTranslator());
			;
	}
	
	
	@Bean
    public WebResponseExceptionTranslator loggingExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                // This is the line that prints the stack trace to the log. You can customise this to format the trace etc if you like
                e.printStackTrace();

                // Carry on handling the exception
                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                OAuth2Exception excBody = responseEntity.getBody();
                return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
            }
        };
    }

	/**
	 * exposing /oauth/check_token and /oauth/token_key endpoints
	 */
	@Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
    }
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public TokenStore tokenStore() {
		// In a real application, we could use a JdbcTokenStore to save them in a database 
		return new InMemoryTokenStore();
	}

}
