package com.app.petsvets.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.petsvets.config.EmptyArgumentException;
import com.app.petsvets.entity.Pet;
import com.app.petsvets.model.PetModel;
import com.app.petsvets.repository.PetRepository;
import com.app.petsvets.service.PetService;

@Service
public class PetServiceImpl implements PetService {

	@Autowired
	private PetRepository petRepo;

	@Override
	public List<Pet> getPetList() {
		return (List<Pet>) petRepo.findAll();
	}

	@Override
	public Optional<Pet> getPetsById(Integer petId) {
		return petRepo.findById(petId);
	}

	/**
	 * {@inheritDoc}
	 * To create new pet
	 * 
	 * @param petModel pet details
	 * @return petModel
	 */
	@Override
	public PetModel createPet(PetModel petModel) {
		if (!petModel.getPetType().isEmpty()) {
			Pet pet = new Pet();
			pet.setPetType(petModel.getPetType());
			pet = petRepo.save(pet);
			petModel.setPetId(pet.getPetId());
		} else {
			throw new EmptyArgumentException("Pet type cannot be empty");
		}
		return petModel;
	}

	@Override
	public Pet getPetByType(String petType) {
		return petRepo.findByPetType(petType);
	}
	
	
}
