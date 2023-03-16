package com.app.petsvets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.petsvets.model.ResponseModel;
import com.app.petsvets.model.UserPetModel;
import com.app.petsvets.service.UserPetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/userpet")
public class UserPetController {

	@Autowired
	private UserPetService userPetService;

	/**
	 * To create new user and pet
	 * 
	 * @param userPet the details pets and user for mapping
	 * @return UserPetModel with created user pet details
	 */
	@ResponseBody
	@PostMapping("/create")
	public ResponseEntity<UserPetModel> createUserPet(@RequestBody UserPetModel userPet, Authentication authentication) {
		log.info("Enabled createUserPet endpoint");
		UserPetModel userPetCreated = userPetService.createUserPet(userPet, authentication.getName());
		return new ResponseEntity<UserPetModel>(userPetCreated, HttpStatus.OK);
	}

	/**
	 * To list pets mapped for an user
	 * 
	 * @param authentication to get userName to retrieve all the mapped pets
	 * @return ResponseModel returns user pet list
	 */
	@ResponseBody
	@GetMapping("/list")
	public ResponseEntity<ResponseModel> getPetsByUser(Authentication authentication) {
		log.info("Enabled getPetsByUser endpoint");
		ResponseModel result = new ResponseModel();
		result.setData(userPetService.getPetsByUser(authentication.getName()));
		result.setMessage("User pet list fetched successfully");
		result.setStatus(true);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.OK);
	}

	/**
	 * To update user pet details
	 * 
	 * @param userPet pet details which needs to modified
	 * @return String Success message
	 */
	@ResponseBody
	@PutMapping("/update")
	public ResponseEntity<ResponseModel> updateUserPet(@RequestBody UserPetModel userPet, Authentication authentication) {
		log.info("Enabled updateUserPet endpoint");
		ResponseModel result = new ResponseModel();
		result.setData(userPetService.updateUserPet(userPet, authentication.getName()));
		result.setMessage("Updated user pet details successfully");
		result.setStatus(true);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.OK);
	}

	/**
	 * To delete pet details mapped against a user
	 * 
	 * @param id user pet mapped id to delete
	 * @return String Success message
	 */
	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseModel> deleteUserPet(@PathVariable Integer id) {
		log.info("Enabled deleteUserPet endpoint");
		ResponseModel result = new ResponseModel();
		result.setData(null);
		result.setMessage(userPetService.deleteUserPet(id));
		result.setStatus(true);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.OK);
	}

}
