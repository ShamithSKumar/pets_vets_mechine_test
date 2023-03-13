package com.app.petsvets.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.petsvets.entity.UserPet;

public interface UserPetRepository extends CrudRepository<UserPet, Integer>{

	List<UserPet> findByUserId(Integer userId);

}
