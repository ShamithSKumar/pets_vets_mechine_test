package com.app.petsvets.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.petsvets.config.CustomException;
import com.app.petsvets.config.EmptyArgumentException;
import com.app.petsvets.entity.Pet;
import com.app.petsvets.entity.UserPet;
import com.app.petsvets.model.PetModel;
import com.app.petsvets.model.UserPetModel;
import com.app.petsvets.repository.UserPetRepository;
import com.app.petsvets.service.UserPetService;
import com.app.petsvets.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserPetServiceImpl implements UserPetService {

	@Autowired
	private UserPetRepository userPetRepo;
	@Autowired
	private UserService userService;
	
	/**
	 * {@inheritDoc}
	 * To create a new user pet mapping
	 * 
	 *  @param userPetModel It contains user pet mapping details
	 *  @return userPetModel returns newly created user pet mapping details
	 *  @throws EmptyArgumentException
	 */
	@Override
	public UserPetModel createUserPet(UserPetModel userPetModel) {
		if (userPetModel != null) {
			UserPet userPetCreated = userPetRepo.save(setUserPet(userPetModel));
			if (userPetCreated != null) {
				userPetModel.setUserPetId(userPetCreated.getUserpetId());
			}
		} else {
			log.error("userPetModel cannot be empty");
			throw new EmptyArgumentException("User pet details cannot be empty");
		}
		return userPetModel;
	}

	/**
	 * {@inheritDoc}
	 * This method sets the user pet details to UserPet entity from the model
	 * 
	 * @param userPetModel Carries user pet details which can map to UserPet entity
	 * @return userPet returns user pet entity
	 */
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

	/**
	 * {@inheritDoc}
	 * To get all the pets mapped to a particular user
	 * 
	 * @param userName to fetch all the pets based on the user mapping
	 * @return userPetList list of pets mapped against the user
	 * @throws EmptyArgumentException, CustomException
	 */
	@Override
	public List<UserPetModel> getPetsByUser(String userName) {
		List<UserPetModel> userPetList = new ArrayList<>();
		if (!userName.isEmpty()) {			
			List<UserPet> userPets = userPetRepo.findByUserUserName(userName);
			if (!userPets.isEmpty()) {
				for (UserPet userPet : userPets) {
					UserPetModel userPetModel = new UserPetModel();
					userPetModel.setNumberOfPets(userPet.getNumberOfPets());
					userPetModel.setUserPetId(userPet.getUserpetId());
					userPetModel.setPet(setPetFromUserPet(userPet.getPet()));
					userPetModel.setUserName(userPet.getUser().getUserName());
					userPetList.add(userPetModel);
				}
			} else {
				log.info("No pets mapped for the user "+userName);
				throw new CustomException("No pets mapped for the user "+userName);
			}
		} else {
			log.error("userName cannot be empty");
			throw new EmptyArgumentException("User name cannot be empty");
		}
		return userPetList;
	}

	/**
	 * {@inheritDoc}
	 * To map pet details to pet model class
	 * 
	 * @param pet Contains pet entity details
	 * @return petModel returns pet model which is created from pet entity
	 */
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
