package com.app.petsvets.model;

import lombok.Data;

@Data
public class UserPetModel {

	private Integer userPetId;
	private String petType;
	private Integer amount;
	private String name;
}
