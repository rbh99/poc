package com.disney.studios.controller;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.disney.studios.domain.Response;
import com.disney.studios.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**
 * global error handling class
 *
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdviceController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ConstraintViolationException.class)
	private ResponseEntity<Object> handleConflict3(ConstraintViolationException ex, WebRequest request) {

		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { BusinessException.class })
	private ResponseEntity<Object> handleConflict12(BusinessException ex, WebRequest request) {

		log.debug("BusinessException exception caught: ", ex);

		Response<?> response = Response.ofFailure(ex.getMessage());
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = { RuntimeException.class })
	private ResponseEntity<Object> handleConflict11(RuntimeException ex, WebRequest request) {

		log.error("Runtime exception caught: ", ex);

		Response<?> response = Response.ofFailure(ex.getMessage());
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}
