package com.simple.echo.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/echo", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class EchoController {
	
	
	@RequestMapping(value = "/**", method = {GET, POST, PUT, DELETE, OPTIONS})
		public ResponseEntity<?> echoBack(@RequestBody(required = false) byte[] rawBody, HttpServletRequest  request) {
			
			Map<String, String> headers  = Collections.list(request.getHeaderNames()
					).stream().collect(Collectors.toMap(Function.identity(), name->request.getHeader(name)));
			
			Map<String, Object> response = new HashMap<>();
			response.put("path", request.getContextPath());
			response.put("protocol", request.getProtocol());
			response.put("method", request.getMethod());
			response.put("headers", headers);
			response.put("cookies", request.getCookies());
			response.put("parameters", request.getParameterMap());
			response.put("request body", rawBody != null ? Base64.getEncoder().encodeToString(rawBody) : null);
			
			
			log.info(" echoing request: {}", response);

			return ResponseEntity.status(HttpStatus.OK).body(response);
		}

}
