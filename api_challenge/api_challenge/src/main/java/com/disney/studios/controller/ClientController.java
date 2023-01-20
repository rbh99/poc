package com.disney.studios.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.disney.studios.domain.VoteRequest;
import com.disney.studios.service.ClientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Validated
public class ClientController {
	
	private final ClientService clientService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/username/{username}")
    public void createByName(@PathVariable("username") @Valid @NotBlank @Size (min=2) String username) {
		
		clientService.addClient(username);
	}
	
	
		@ResponseStatus(HttpStatus.CREATED)
		@PostMapping("/vote")
		public void vote(@RequestBody @Valid VoteRequest voteRequest) {
			
			log.debug("voting {}", voteRequest);

			clientService.vote(voteRequest);
			
		}
}
