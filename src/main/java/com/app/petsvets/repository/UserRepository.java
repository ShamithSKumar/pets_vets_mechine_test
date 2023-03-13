package com.app.petsvets.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.petsvets.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	Iterable<User> findAllByRole(String role);

	
}
