package com.app.petsvets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.petsvets.model.ResponseModel;
import com.app.petsvets.model.UserLoginModel;
import com.app.petsvets.model.UserModel;
import com.app.petsvets.service.JwtService;
import com.app.petsvets.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authManager;

	/**
	 * To authenticate user and to generate JWT token
	 * 
	 * @param login user name and password as requestBody to validate the user
	 * @return ResponseModel JWT token
	 */
	@ResponseBody
	@PostMapping("/login")
	public ResponseEntity<ResponseModel> authenticateAndGetToken(@RequestBody UserLoginModel login, Authentication authentication) {
		
		log.info("Login api endpoint enabled");
		ResponseModel result = new ResponseModel();
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
		if (auth.isAuthenticated()) {
			result.setData(jwtService.generateToken(login.getUserName(), auth));
			result.setMessage("Token created successfully");
			result.setStatus(true);
			return new ResponseEntity<ResponseModel>(result, HttpStatus.OK);
		} else {
			throw new UsernameNotFoundException("inValid user request !");
		}

	}

	/**
	 * To get all users, only user with role ADMIN can access this end point
	 * 
	 * @return List of UserModel
	 */
	@ResponseBody
	@PreAuthorize("hasAuthority(ROLE_ADMIN)")
	@GetMapping("/list")
	public ResponseEntity<ResponseModel> getAllUsers() {
		log.info("Enabled user list endpoint by admin");
		ResponseModel result = new ResponseModel();
		result.setData(userService.getAllUsers());
		result.setMessage("Users fetched successfully");
		result.setStatus(true);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.OK);
	}

	/**
	 * To create user USER role will be added for all the new users
	 * 
	 * @param user all user details as requestBody
	 * @return String Success message
	 */
	@ResponseBody
	@PostMapping("/create")
	public ResponseEntity<ResponseModel> createUser(@RequestBody UserModel user) {
		log.info("Enabled create user endpoint");
		ResponseModel result = new ResponseModel();
		result.setData(null);
		result.setMessage(userService.createUser(user));
		result.setStatus(true);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.OK);
	}

	/**
	 * To get user by user id
	 * 
	 * @param id user unique id to fetch the user
	 * @return UserModel with details
	 */
	@ResponseBody
	@GetMapping("/{id}")
	public ResponseEntity<ResponseModel> getUser(@PathVariable Integer id) {
		log.info("Enabled get user endpoint");
		ResponseModel result = new ResponseModel();
		result.setData(userService.getUser(id));
		result.setMessage("User fetched successfully");
		result.setStatus(true);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.OK);
	}

	/**
	 * To update existing user
	 * 
	 * @param user updated user details as requestBody
	 * @return String Success message
	 */
	@ResponseBody
	@PutMapping("/update")
	public ResponseEntity<ResponseModel> updateUser(@RequestBody UserModel user, Authentication authentication) {
		log.info("Enabled update user endpoint");
		ResponseModel result = new ResponseModel();
		result.setData(userService.updateUser(user, authentication));
		result.setMessage("User updated successfully");
		result.setStatus(true);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.OK);
	}

	/**
	 * To logout the current user
	 * 
	 * @return Success message
	 */
	@ResponseBody
	@PostMapping("/logout")
	public ResponseEntity<ResponseModel> logout() {
		log.info("Enabled logout endpoint");
		ResponseModel result = new ResponseModel();
		result.setData(null);
		result.setMessage("You have been logged out successfully");
		result.setStatus(true);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.OK);
	}

}
