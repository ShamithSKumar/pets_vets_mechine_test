package com.app.petsvets.service;

import java.util.List;

import com.app.petsvets.model.UserPetModel;

public interface UserPetService {

	UserPetModel createUserPet(UserPetModel userPet);

	List<UserPetModel> getPetsByUser(String userName);

	String updateUserPet(UserPetModel userPet);

	String deleteUserPet(Integer id);

}
