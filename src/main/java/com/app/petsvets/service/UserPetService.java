package com.app.petsvets.service;

import java.util.List;

import com.app.petsvets.entity.UserPet;
import com.app.petsvets.model.UserPetModel;

public interface UserPetService {

	UserPetModel createUserPet(UserPetModel userPet);

	List<UserPet> getPetsByUserId(Integer userId);

}
