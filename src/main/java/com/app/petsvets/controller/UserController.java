package com.app.petsvets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.petsvets.entity.User;
import com.app.petsvets.entity.UserLogin;
import com.app.petsvets.model.UserDetailsModel;
import com.app.petsvets.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public @ResponseBody List<UserDetailsModel> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody() User user) {
//		User user = new User();
		user.setUserName("Shamith");
		user.setEmail("shamithshasikumar@gmail.com");
		user.setPassword("123456");
		user.setPhone("+91-8281677453");
		user.setRole("admin");
		User newUser = userService.createUser(user);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserLogin> userLogin(@RequestBody() UserLogin user) {
		return new ResponseEntity<UserLogin>(user, HttpStatus.OK);
	}
}
