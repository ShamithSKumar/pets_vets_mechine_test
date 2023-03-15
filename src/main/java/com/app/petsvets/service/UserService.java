package com.app.petsvets.service;

import java.util.List;
import java.util.Optional;

import com.app.petsvets.entity.User;
import com.app.petsvets.model.UserModel;

public interface UserService {

	List<UserModel> getAllUsers();

	String createUser(User user);

	Optional<User> findByUserName(String userName);

	UserModel getUser(Integer id);

	String updateUser(User user);

}
