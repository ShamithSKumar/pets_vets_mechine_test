package com.app.petsvets.model;

import lombok.Data;

@Data
public class UserDetailsModel {
	
	private Integer userId;
	private String userName;
	private String email;
	private String phone;
	private String password;
	private String role;
}
