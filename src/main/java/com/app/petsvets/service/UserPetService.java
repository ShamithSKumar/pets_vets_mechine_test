package com.app.petsvets.service;

import java.util.List;

import com.app.petsvets.model.UserPetModel;

public interface UserPetService {

	UserPetModel createUserPet(UserPetModel userPet, String userName);

	List<UserPetModel> getPetsByUser(String userName);

	String updateUserPet(UserPetModel userPet, String userName);

	String deleteUserPet(Integer id);

}
