package com.app.petsvets.model;

import lombok.Data;

@Data
public class UserPetModel {

	private Integer userPetId;
	private UserModel user;
	private PetTypeModel pet;
	private String petName;
}
