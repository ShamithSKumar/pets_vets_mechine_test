package com.app.petsvets.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.petsvets.entity.UserPet;
import com.app.petsvets.model.PetTypeModel;
import com.app.petsvets.model.UserModel;
import com.app.petsvets.model.UserPetModel;
import com.app.petsvets.repository.UserPetRepository;
import com.app.petsvets.service.PetService;
import com.app.petsvets.service.UserPetService;

@Service
public class UserPetServiceImpl implements UserPetService {

	@Autowired
	private UserPetRepository userPetRepo;
	@Autowired
	private PetService userPetService;
	
	@Override
	public UserPetModel createUserPet(UserPetModel userPetModel) {
		UserPet userPet = new UserPet();
		userPet.setPet(userPetService.getPetsById(userPetModel.getPet().getPetId()).get());
		userPet.setUser(null); //TODO
		userPet.setPetName(userPetModel.getPetName());
		UserPet userPetCreated = userPetRepo.save(userPet);
		
		UserPetModel userPetModelCreated = new UserPetModel();
		PetTypeModel pet = new PetTypeModel();
		pet.setPetId(userPetCreated.getPet().getPetId());
		pet.setPetType(userPetCreated.getPet().getPetType());
		userPetModelCreated.setPet(pet);
		UserModel user = new UserModel();
		user.setUserId(userPetCreated.getUser().getUserId());
		user.setUserName(userPetCreated.getUser().getUserName());
		userPetModelCreated.setUser(user);
		userPetModelCreated.setPetName(userPetCreated.getPetName());
		userPetModelCreated.setUserPetId(userPetCreated.getUserpetId());
		return userPetModelCreated;
	}

	@Override
	public List<UserPet> getPetsByUserId(Integer userId) {
		List<UserPet> userPets = userPetRepo.findByUserUserId(userId);
		return userPets;
	}

}
