package com.devskiller.tasks.blog.rest;


import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.devskiller.tasks.blog.exception.BusinessException;



@ControllerAdvice
public class ExceptionHandlerAdvice  extends ResponseEntityExceptionHandler{
	
	//private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class.getName());
	
	
	@ExceptionHandler(value = { BusinessException.class })
	 private ResponseEntity<Object> handleConflictBusiness(BusinessException ex, WebRequest request) {
		
	     return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.CONFLICT, request);
	 }
	
	@ExceptionHandler(value = { RuntimeException.class })
	 private ResponseEntity<Object> handleConflictRuntime(BusinessException ex, WebRequest request) {
	     return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	 }
	
	

	@ExceptionHandler(value = ConstraintViolationException.class )
	 private ResponseEntity<Object> handleConflictValidation(ConstraintViolationException ex, WebRequest request) {
	    
	     return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	 }
}
