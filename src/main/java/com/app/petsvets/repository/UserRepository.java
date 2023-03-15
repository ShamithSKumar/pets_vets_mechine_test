package com.app.petsvets.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.petsvets.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	Iterable<User> findAllByRole(String role);

	Optional<User> findByUserName(String userName);

	//User findByUserName(String userName);

	
}
