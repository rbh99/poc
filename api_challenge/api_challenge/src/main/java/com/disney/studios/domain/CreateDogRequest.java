package com.disney.studios.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class CreateDogRequest {
	
	private final String dogName;
	
	private final String breedName;
	
	@Singular("pictureUri")
	private List<String> pictureUris;

}
