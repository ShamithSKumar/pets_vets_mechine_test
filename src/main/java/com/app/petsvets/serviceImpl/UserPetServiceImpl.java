package com.app.petsvets.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.petsvets.entity.Pet;
import com.app.petsvets.entity.UserPet;
import com.app.petsvets.model.PetModel;
import com.app.petsvets.model.UserPetModel;
import com.app.petsvets.repository.UserPetRepository;
import com.app.petsvets.service.UserPetService;
import com.app.petsvets.service.UserService;

@Service
public class UserPetServiceImpl implements UserPetService {

	@Autowired
	private UserPetRepository userPetRepo;
	@Autowired
	private UserService userService;
	
	@Override
	public UserPetModel createUserPet(UserPetModel userPetModel) {
		UserPet userPetCreated = userPetRepo.save(setUserPet(userPetModel));
		if (userPetCreated != null) {
			userPetModel.setUserPetId(userPetCreated.getUserpetId());
		}
		return userPetModel;
	}

	private UserPet setUserPet(UserPetModel userPetModel) {
		UserPet userPet = new UserPet();
		Pet pet = new Pet();
		pet.setPetId(userPetModel.getPet().getPetId());
		pet.setPetType(userPetModel.getPet().getPetType());
		userPet.setPet(pet);
		userPet.setNumberOfPets(userPetModel.getNumberOfPets());
		userPet.setUser(userService.findByUserName(userPetModel.getUserName()).get());
		return userPet;
	}

	@Override
	public List<UserPetModel> getPetsByUserId(String userName) {
		List<UserPet> userPets = userPetRepo.findByUserUserName(userName);
		List<UserPetModel> userPetList = new ArrayList<>();
		if (!userPets.isEmpty()) {
			for (UserPet userPet : userPets) {
				UserPetModel userPetModel = new UserPetModel();
				userPetModel.setNumberOfPets(userPet.getNumberOfPets());
				userPetModel.setUserPetId(userPet.getUserpetId());
				userPetModel.setPet(setPetFromUserPet(userPet.getPet()));
				userPetModel.setUserName(userPet.getUser().getUserName());
				userPetList.add(userPetModel);
			}
		}
		return userPetList;
	}

	private PetModel setPetFromUserPet(Pet pet) {
		PetModel petModel = new PetModel();
		petModel.setPetId(pet.getPetId());
		petModel.setPetType(pet.getPetType());
		return petModel;
	}

	@Override
	public String updateUserPet(UserPetModel userPet) {
		userPetRepo.save(setUserPet(userPet));
		return "User updated successfully";
	}

	@Override
	public String deleteUserPet(Integer id) {
		userPetRepo.deleteById(id);
		return "User deleted successfully";
	}

}
