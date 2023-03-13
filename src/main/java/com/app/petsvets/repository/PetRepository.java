package com.app.petsvets.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.petsvets.entity.Pet;

@Repository
public interface PetRepository extends CrudRepository<Pet, Integer>{

}
