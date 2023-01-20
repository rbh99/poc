package com.devskiller.tasks.blog.exception;

/**
 * thrown by service Layer
 *
 *
 */
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = 7218823143002016589L;

	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
