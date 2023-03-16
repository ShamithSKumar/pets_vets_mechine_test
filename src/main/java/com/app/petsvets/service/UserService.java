package com.app.petsvets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.app.petsvets.entity.User;
import com.app.petsvets.model.UserModel;

public interface UserService {

	List<UserModel> getAllUsers();

	String createUser(UserModel user);

	Optional<User> findByUserName(String userName);

	UserModel getUser(Integer id);

	String updateUser(UserModel user, Authentication authentication);

}
