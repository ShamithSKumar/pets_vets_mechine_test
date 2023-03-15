package com.app.petsvets.config;

public class EmptyArgumentException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public EmptyArgumentException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
