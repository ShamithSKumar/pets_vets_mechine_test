package com.app.petsvets.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.petsvets.entity.Pet;
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
	public Pet CreatePet(Pet pet) {
		Pet petCreated = petRepo.save(pet);
		return petCreated;
	}

	@Override
	public Optional<Pet> getPetsById(Integer petId) {
		return petRepo.findById(petId);
	}
	
	
}
