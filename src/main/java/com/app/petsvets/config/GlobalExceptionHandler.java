package com.app.petsvets.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.petsvets.model.ResponseModel;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<String> customExceptionHandler(CustomException customException) {
		return new ResponseEntity<String>(customException.getErrorMessage(), HttpStatus.OK);
	}
	
	@ResponseBody
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ResponseModel> useNameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
		ResponseModel result = new ResponseModel();
		result.setData(null);
		result.setMessage(usernameNotFoundException.getMessage());
		result.setStatus(false);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseBody
	@ExceptionHandler(EmptyArgumentException.class)
	public ResponseEntity<ResponseModel> emptyArgumentException(EmptyArgumentException emptyArgumentException) {
		ResponseModel result = new ResponseModel();
		result.setData(null);
		result.setMessage(emptyArgumentException.getErrorMessage());
		result.setStatus(false);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseBody
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return new ResponseEntity<Object>("Please change your http method type", HttpStatus.BAD_REQUEST);
	}

}
