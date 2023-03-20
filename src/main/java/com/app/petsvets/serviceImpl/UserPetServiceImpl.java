package com.app.petsvets.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.petsvets.config.CustomException;
import com.app.petsvets.config.EmptyArgumentException;
import com.app.petsvets.entity.Pet;
import com.app.petsvets.entity.UserPet;
import com.app.petsvets.model.UserPetModel;
import com.app.petsvets.repository.UserPetRepository;
import com.app.petsvets.service.PetService;
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
	@Autowired
	private PetService petService;
	
	/**
	 * {@inheritDoc}
	 * To create a new user pet mapping
	 * 
	 *  @param userPetModel It contains user pet mapping details
	 *  @return userPetModel returns newly created user pet mapping details
	 *  @throws EmptyArgumentException
	 */
	@Override
	public UserPetModel createUserPet(UserPetModel userPetModel, String userName) {
		if (userPetModel != null) {
			UserPet userPetCreated = userPetRepo.save(setUserPet(userPetModel, userName));
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
	 * @param userName 
	 * @return userPet returns user pet entity
	 */
	private UserPet setUserPet(UserPetModel userPetModel, String userName) {
		UserPet userPet = new UserPet();
		if (userPetModel.getUserPetId() != null) 
			userPet.setUserpetId(userPetModel.getUserPetId());
		Pet pet = petService.getPetByType(userPetModel.getPetType());
		userPet.setPet(pet);
		userPet.setAmount(userPetModel.getAmount());
		userPet.setUser(userService.findByUserName(userName).get());
		userPet.setName(userPetModel.getName());
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
					userPetModel.setAmount(userPet.getAmount());
					userPetModel.setUserPetId(userPet.getUserpetId());
					userPetModel.setPetType(userPet.getPet().getPetType());
					userPetModel.setName(userPet.getName());
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

	@Override
	public String updateUserPet(UserPetModel userPet, String userName) {
		userPetRepo.save(setUserPet(userPet, userName));
		return "User updated successfully";
	}

	@Override
	public String deleteUserPet(Integer id) {
		userPetRepo.deleteById(id);
		return "User deleted successfully";
	}

}
