package com.app.petsvets.model;

import lombok.Data;

@Data
public class LoginResponseModel {

	private String userName;
	private String role;
	private String token;
}
