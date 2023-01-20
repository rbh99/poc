package com.example.resourceserver.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SecuredRestController {
	
	//called with Header Authorization   Bearer <and the token value here>
	
	//@PreAuthorize("hasAuthority('FOO_READ')")
	@GetMapping(value="/foo")
	public String readFoo(Principal principal) {
		log.info("reading foo principal {}", principal);
		return "read foo "+principal.getName();
	}

	@PreAuthorize("hasAuthority('FOO_WRITE')")
	@PostMapping(value="/foo")
	public String writeFoo() {
		log.info("writing foo");
		return "write foo ";
	}

}
