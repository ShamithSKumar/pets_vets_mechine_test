package com.app.petsvets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.petsvets.entity.User;
import com.app.petsvets.model.UserLoginModel;
import com.app.petsvets.model.UserModel;
import com.app.petsvets.service.UserService;
import com.app.petsvets.serviceImpl.JwtServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtServiceImpl jwtService;
	@Autowired
	private AuthenticationManager authManager;

	/**
	 * To authenticate user and to generate JWT token
	 * 
	 * @param login user name and password as requestBody to validate the user
	 * @return String JWT token
	 */
	@PostMapping("/login")
	public ResponseEntity<String> authenticateAndGetToken(@RequestBody UserLoginModel login) {
		log.info("Login api endpoint enabled");
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
		if (auth.isAuthenticated()) {
			return new ResponseEntity<String>(jwtService.generateToken(login.getUserName()), HttpStatus.OK);
		} else {
			throw new UsernameNotFoundException("inValid user request !");
		}

	}

	/**
	 * To get all users only user with role ADMIN can access this end point
	 * 
	 * @return List of UserModel
	 */
	@PreAuthorize("hasAuthority(ROLE_ADMIN)")
	@GetMapping("/list")
	public ResponseEntity<List<UserModel>> getAllUsers() {
		log.info("Enabled user list endpoint by admin");
		return new ResponseEntity<List<UserModel>>(userService.getAllUsers(), HttpStatus.OK);
	}

	/**
	 * To create user USER role will be added for all the new users
	 * 
	 * @param user all user details as requestBody
	 * @return String Success message
	 */
	@PostMapping("/create")
	public ResponseEntity<String> createUser(@RequestBody() User user) {
		log.info("Enabled create user endpoint");
		return new ResponseEntity<String>(userService.createUser(user), HttpStatus.OK);
	}

	/**
	 * To get user by user id
	 * 
	 * @param id user unique id to fetch the user
	 * @return UserModel with details
	 */
	@GetMapping("/{id}")
	public ResponseEntity<UserModel> getUser(@PathVariable Integer id) {
		log.info("Enabled get user endpoint");
		return new ResponseEntity<UserModel>(userService.getUser(id), HttpStatus.OK);
	}

	/**
	 * To update existing user
	 * 
	 * @param user updated user details as requestBody
	 * @return String Success message
	 */
	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody User user) {
		log.info("Enabled update user endpoint");
		return new ResponseEntity<String>(userService.updateUser(user), HttpStatus.OK);
	}

	// TODO Admin should be able to update the user expect username.

}
