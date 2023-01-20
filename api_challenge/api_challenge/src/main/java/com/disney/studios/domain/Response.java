package com.disney.studios.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Response<T> {
	
	@NonNull
	private final Status status;

	//@Nullable
	private final T data;
	
	public static <T> Response<T> ofSuccess(T data){
		return new Response<T>(new Status(StatusType.SUCCESS, null), data);
	}
	
	
	public static <T> Response<T> ofFailure(String message){
		return new Response<T>(new Status(StatusType.FAILURE, message), null);
	}
	
	@Data
	@AllArgsConstructor (access = AccessLevel.PRIVATE)
	static class Status {
		
		private final StatusType type;
		
		private final String message;
	}
	
	enum StatusType {
		SUCCESS,
		FAILURE
	}
}
