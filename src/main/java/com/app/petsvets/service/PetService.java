package com.app.petsvets.service;

import java.util.List;
import java.util.Optional;

import com.app.petsvets.entity.Pet;

public interface PetService {

	List<Pet> getPetList();

	Pet CreatePet(Pet pet);

	Optional<Pet> getPetsById(Integer petId);

}
