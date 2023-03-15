package com.app.petsvets.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<String> customExceptionHandler(CustomException customException) {
		return new ResponseEntity<String>(customException.getErrorMessage(), HttpStatus.OK);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<String> useNameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
		return new ResponseEntity<String>(usernameNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmptyArgumentException.class)
	public ResponseEntity<String> emptyArgumentException(EmptyArgumentException emptyArgumentException) {
		return new ResponseEntity<String>(emptyArgumentException.getErrorMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return new ResponseEntity<Object>("Please change your http method type", HttpStatus.BAD_REQUEST);
	}

}
