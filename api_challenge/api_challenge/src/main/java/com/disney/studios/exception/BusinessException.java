package com.disney.studios.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 2279194308747883914L;
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
