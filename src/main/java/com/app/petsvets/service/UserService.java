package com.app.petsvets.service;

import java.util.List;

import com.app.petsvets.entity.User;
import com.app.petsvets.model.UserDetailsModel;

public interface UserService {

	List<UserDetailsModel> getAllUsers();

	User createUser(User user);

}
