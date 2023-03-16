package com.app.petsvets.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
		if (!user.isEmpty()) {
			return user.map(UserDetailsModel::new)
					.orElseThrow(()->new UsernameNotFoundException("User not found "+userName));
		} else {
			throw new EmptyArgumentException("User not found "+userName);
		}
		
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
	 * @param user User model with user details
	 * @return String user created message
	 * @throws EmptyArgumentException
	 */
	@Override
	public String createUser(UserModel userModel) {
		if (userModel != null) {
			userRepo.save(setUser(userModel));
		} else {
			log.error("User entity cannot be empty");
			throw new EmptyArgumentException("User data cannot be empty");
		}
		return "Üser created";
	}

	/**
	 * Method to set user entity for update and create
	 * 
	 * @param userModel for user details
	 * @return user entity
	 */
	private User setUser(UserModel userModel) {
		User user = new User();
		if (userModel.getId() == null) {
			user.setPassword(passwordEncoder.encode(userModel.getPassword()));
			user.setRole(ROLE);
		} else {
			user.setUserId(userModel.getId());
		}
		user.setEmail(userModel.getEmail());
		user.setPhone(userModel.getPhone());
		user.setUserName(userModel.getUserName());
		return user;
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

	/**
	 * {@inheritDoc}
	 * To update existing user, 
	 * both ROLE_ADMIN and ROLE_USER can update the user
	 * but the ROLE_ADMIN cannot update the userName
	 * 
	 * @param userModel to get user details
	 * @param authentication to get userName of the login user
	 * @return String user updated message
	 */
	@Override
	public String updateUser(UserModel userModel, Authentication authentication) {
		String userRole = getUserRole(authentication);
		if (userRole.matches("ROLE_ADMIN")) {
			Optional<User> user = userRepo.findByUserName(userModel.getUserName());
			if (!user.isPresent()) {
				throw new CustomException("Admin cannot update user name");				
			} else {
				userRepo.save(setUser(userModel));
			}
		}
		return "User updated successfully";
	}

	private String getUserRole(Authentication authentication) {
		return authentication.getAuthorities().stream().findFirst().toString();
	}

}
