package com.disney.studios.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PictureSummary {
	
	private final int id;
	
	private final String uri;
	
	private final int dogId;
	
	private final String dogName;
	
	private final String breed;
	
	private final long votes;
	
	private final long total;

}
