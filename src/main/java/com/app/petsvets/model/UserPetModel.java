package com.app.petsvets.model;

import lombok.Data;

@Data
public class UserPetModel {

	private Integer userPetId;
	private PetModel pet;
	private Integer numberOfPets;
	private String userName;
}
