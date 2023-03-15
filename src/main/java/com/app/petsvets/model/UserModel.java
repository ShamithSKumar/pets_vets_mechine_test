package com.app.petsvets.model;

import lombok.Data;

@Data
public class UserModel {
	
	private Integer id;
	private String userName;
	private String email;
	private String phone;
	private String role;
}
