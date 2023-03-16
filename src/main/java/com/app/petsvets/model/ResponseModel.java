package com.app.petsvets.model;

import lombok.Data;

@Data
public class ResponseModel {

	private Object data;
	private String message;
	private boolean status;
}
