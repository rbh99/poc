package com.disney.studios.domain;

import javax.validation.Valid;

import lombok.Data;

@Data
public class VoteRequest {

	private int clientId;

	private int pictureId;

	private int mark;
	
	@Valid
	private boolean isMarkValid(){
		return mark ==1 || mark == -1;
	}

}
