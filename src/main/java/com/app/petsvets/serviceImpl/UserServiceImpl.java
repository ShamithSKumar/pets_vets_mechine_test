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

import com.app.petsvets.entity.User;
import com.app.petsvets.model.UserDetailsModel;
import com.app.petsvets.model.UserModel;
import com.app.petsvets.repository.UserRepository;
import com.app.petsvets.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final String ROLE = "ROLE_USER";
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUserName(userName);
		return user.map(UserDetailsModel::new)
				.orElseThrow(()->new UsernameNotFoundException("User not found "+userName));
	}

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

	@Override
	public String createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		return "Üser created";
	}

	@Override
	public Optional<User> findByUserName(String userName) {
		return userRepo.findByUserName(userName);
	}

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
			// throw error
		}
		return userModel;
	}

	@Override
	public String updateUser(User user) {
		userRepo.save(user);
		return "User updated successfully";
	}

}
