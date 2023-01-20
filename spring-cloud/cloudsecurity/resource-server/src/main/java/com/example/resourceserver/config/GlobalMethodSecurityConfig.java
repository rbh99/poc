package com.example.resourceserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * 
 *
 *   The prePostEnabled property enables Spring Security pre/post annotations
 *   	Both @PreAuthorize and @PostAuthorize annotations provide expression-based access control. 
 *         The @PreAuthorize annotation checks the given expression before entering the method, whereas, 
 *         the @PostAuthorize annotation verifies it after the execution of the method and could alter the result.
 *         @PreAuthorize("hasRole('ROLE_VIEWER')") @PreAuthorize("hasRole('ROLE_VIEWER') or hasRole('ROLE_EDITOR')")
 *         Moreover, we can actually use the method argument as part of the expression: 
 *         				@PreAuthorize("#username == authentication.principal.username")
 *						public String getMyRoles(String username)
 *
 *         Additionally, the @PostAuthorize annotation provides the ability to access the method result:
 *         	
 *         @PostAuthorize
 *           ("returnObject.username == authentication.principal.nickName")
 *         public CustomUser loadUserDetail(String username) {
 *             return userRoleRepository.loadUserByUserName(username);
 *         }
 *         
 *         In this example, the loadUserDetail method would only execute successfully if the username of the returned CustomUser is equal to the current authentication principal's nickname.
 *
 *
 *         Using @PreFilter and @PostFilter Annotations
 *			Spring Security provides the @PreFilter annotation to filter a collection argument before executing the method:
 *			@PreFilter("filterObject != authentication.principal.username")
 *			public String joinUsernames(List<String> usernames) {
 *          //Here, our expression uses the name filterObject to represent the current object in the collection.
 *          use filterTarget = "usernames" argument in @PreFilter if the method has more than one list as arguments
 *
 *  we can define a security meta-annotation:
 *  
 *  @Target(ElementType.METHOD)
 *  @Retention(RetentionPolicy.RUNTIME)
 *  @PreAuthorize("hasRole('VIEWER')")
 *  public @interface IsViewer {
 *  }
 *  
 *  Next, we can directly use the @IsViewer annotation to secure our method:
 *  @IsViewer
 *  public String getUsername4() {
 *      //...
 *  }
 *
 *			
 *   
 *   The securedEnabled property determines if the @Secured annotation should be enabled
 *   	The @Secured annotation is used to specify a list of roles on a method. @Secured("ROLE_VIEWER")
 *   The jsr250Enabled property allows us to use the @RoleAllowed annotation
 *   	The @RoleAllowed annotation is the JSR-250’s equivalent annotation of the @Secured annotation.  @RolesAllowed("ROLE_VIEWER")
 *
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, 
securedEnabled = true, 
jsr250Enabled = true)
public class GlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

}
