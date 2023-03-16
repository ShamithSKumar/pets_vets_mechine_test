package com.app.petsvets.service;

import java.util.List;
import java.util.Optional;

import com.app.petsvets.entity.Pet;
import com.app.petsvets.model.PetModel;

public interface PetService {

	List<Pet> getPetList();

	PetModel createPet(PetModel pet);

	Optional<Pet> getPetsById(Integer petId);

	Pet getPetByType(String petType);

}
