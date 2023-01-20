package com.example.authserver.controller;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/user/me")
	public Principal user(Principal principal) {
		return principal;
	}
	
	@GetMapping("/whoami")
	public String whoami(@AuthenticationPrincipal(expression="name") String name) {
		return name;
    }

}
