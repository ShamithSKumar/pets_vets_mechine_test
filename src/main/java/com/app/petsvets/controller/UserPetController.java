package com.app.petsvets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.petsvets.model.UserPetModel;
import com.app.petsvets.service.UserPetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	@PostMapping("/create")
	public ResponseEntity<UserPetModel> createUserPet(@RequestBody() UserPetModel userPet) {
		log.info("Enabled createUserPet endpoint");
		UserPetModel userPetCreated = userPetService.createUserPet(userPet);
		return new ResponseEntity<UserPetModel>(userPetCreated, HttpStatus.OK);
	}

	/**
	 * To list pets mapped for an user
	 * 
	 * @param userName user name to retrieve all the mapped pets
	 * @return
	 */
	@GetMapping("/list/{userName}")
	public ResponseEntity<List<UserPetModel>> getPetsByUser(@PathVariable String userName) {
		log.info("Enabled getPetsByUser endpoint");
		List<UserPetModel> userPets = userPetService.getPetsByUser(userName);
		return new ResponseEntity<List<UserPetModel>>(userPets, HttpStatus.OK);
	}

	/**
	 * To update user pet details
	 * 
	 * @param userPet pet details which needs to modified
	 * @return String Success message
	 */
	@PutMapping("/update")
	public ResponseEntity<String> updateUserPet(@RequestBody UserPetModel userPet) {
		log.info("Enabled updateUserPet endpoint");
		return new ResponseEntity<String>(userPetService.updateUserPet(userPet), HttpStatus.OK);
	}

	/**
	 * To delete pet details mapped against a user
	 * 
	 * @param id user pet mapped id to delete
	 * @return String Success message
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUserPet(@PathVariable Integer id) {
		log.info("Enabled deleteUserPet endpoint");
		return new ResponseEntity<String>(userPetService.deleteUserPet(id), HttpStatus.OK);
	}

}
