package com.disney.studios.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DogSummary {
	
	private final int id;
	
	private final String name;
	
	private final String breed;

}
