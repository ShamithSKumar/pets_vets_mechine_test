package com.app.petsvets.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.petsvets.config.CustomException;
import com.app.petsvets.config.EmptyArgumentException;
import com.app.petsvets.entity.User;
import com.app.petsvets.model.UserDetailsModel;
import com.app.petsvets.model.UserModel;
import com.app.petsvets.repository.UserRepository;
import com.app.petsvets.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final String ROLE = "ROLE_USER";
	
	/**
	 * {@inheritDoc}
	 * To get user details from db using user name
	 * 
	 * @param userName user name for fetching user details
	 * @return userDetailsModel the user details is then mapped 
	 * to UserDetailsModel class which implements spring security UserDetails class
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) {
		Optional<User> user = userRepo.findByUserName(userName);
		return user.map(UserDetailsModel::new)
				.orElseThrow(()->new UsernameNotFoundException("User not found "+userName));
	}

	/**
	 * {@inheritDoc}
	 * To get all the users from db
	 * 
	 * @return userList list of users
	 */
	@Override
	public List<UserModel> getAllUsers() {
		Iterable<User> users = userRepo.findAllByRole(ROLE);
		List<UserModel> userList = new ArrayList<>();
		for (User user : users) {
			UserModel userModel = new UserModel();
			userModel.setId(user.getUserId());
			userModel.setUserName(user.getUserName());
			userModel.setEmail(user.getEmail());
			userModel.setPhone(user.getPhone());
			userModel.setRole(user.getRole());
			userList.add(userModel);
		}
		return userList;
	}

	/**
	 * {@inheritDoc}
	 * To create new user, the password is encoded before saving to db
	 * 
	 * @param user User entity to create user
	 * @return String user created message
	 * @throws EmptyArgumentException
	 */
	@Override
	public String createUser(User user) {
		if (user != null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepo.save(user);
		} else {
			log.error("User entity cannot be empty");
			throw new EmptyArgumentException("User data cannot be empty");
		}
		return "Üser created";
	}

	@Override
	public Optional<User> findByUserName(String userName) {
		return userRepo.findByUserName(userName);
	}

	/**
	 * {@inheritDoc}
	 * To get user by unique id
	 * 
	 * @param id unique user id
	 * @return userModel user entity is mapped to user model
	 * @throws CustomException
	 */
	@Override
	public UserModel getUser(Integer id) {
		Optional<User> user = userRepo.findById(id);
		UserModel userModel = new UserModel();
		if (user.isPresent()) {
			userModel.setId(user.get().getUserId());
			userModel.setUserName(user.get().getUserName());
			userModel.setEmail(user.get().getEmail());
			userModel.setPhone(user.get().getPhone());
			userModel.setRole(user.get().getRole());
		} else {
			log.info("No user available for id "+id);
			throw new CustomException("No user available for id "+id);
		}
		return userModel;
	}

	@Override
	public String updateUser(User user) {
		userRepo.save(user);
		return "User updated successfully";
	}

}
