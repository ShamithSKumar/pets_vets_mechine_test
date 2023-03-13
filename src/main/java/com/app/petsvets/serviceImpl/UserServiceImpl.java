package com.app.petsvets.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.petsvets.entity.User;
import com.app.petsvets.model.UserDetailsModel;
import com.app.petsvets.repository.UserRepository;
import com.app.petsvets.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Transactional
	@Override
	public List<UserDetailsModel> getAllUsers() {
		Iterable<User> userList = userRepo.findAllByRole("user");
		List<UserDetailsModel> users = new ArrayList<>();
		for(User user : userList ) {
			UserDetailsModel userDetails = new UserDetailsModel();
			userDetails.setUserId(user.getUserId());
			userDetails.setUserName(user.getUserName());
			userDetails.setEmail(user.getEmail());
			userDetails.setPhone(user.getPhone());
			users.add(userDetails);
		}
		
		return users;
	}

	@Override
	public User createUser(User user) {
		User newUser = userRepo.save(user);
		return newUser;
	}

}
