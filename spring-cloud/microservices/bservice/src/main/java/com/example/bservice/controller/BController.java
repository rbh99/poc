package com.example.bservice.controller;

import java.nio.CharBuffer;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BController {

	@GetMapping(value = "/bstatus/{name}")
	public String getServiceBDtata(@PathVariable("name") String name) {

		log.debug("calling getServiceBDtata with {}", name);
		
		//not efficient, but ok
		String modif = CharBuffer.wrap(name).chars().mapToObj(ch -> String.valueOf((char) ch))
				.collect(Collectors.joining("_"));

		log.debug("calling getServiceBDtata with {}. returning modifcated string {}", name, modif);
		return modif;
	}

}
