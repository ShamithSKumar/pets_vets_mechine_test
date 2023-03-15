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

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JwtServiceImpl jwtService;
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/login")
	public ResponseEntity<String> authenticateAndGetToken(@RequestBody UserLoginModel login) {
		Authentication auth =  authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
		if (auth.isAuthenticated()) {
			return new ResponseEntity<String>(jwtService.generateToken(login.getUserName()), HttpStatus.OK);
		} else {
			throw new UsernameNotFoundException("inValid user request !");
		}
		
	}
	
	@PreAuthorize("hasAuthority(ROLE_ADMIN)")
	@GetMapping("/list")
	public ResponseEntity<List<UserModel>> getAllUsers() {
		return new ResponseEntity<List<UserModel>>(userService.getAllUsers(), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<String> createUser(@RequestBody() User user) {
		return new ResponseEntity<String>(userService.createUser(user), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserModel> getUser(@PathVariable Integer id) {
		return new ResponseEntity<UserModel>(userService.getUser(id), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody User user ) {
		return new ResponseEntity<String>(userService.updateUser(user), HttpStatus.OK);
	}
	
	//TODO Admin should be able to update the user expect username.
	
}
