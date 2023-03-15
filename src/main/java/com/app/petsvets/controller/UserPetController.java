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

@RestController
@RequestMapping("/userpet")
public class UserPetController {

	@Autowired
	private UserPetService userPetService;
	
	@PostMapping("/create")
	public ResponseEntity<UserPetModel> createUserPet(@RequestBody() UserPetModel userPet) {
		UserPetModel userPetCreated = userPetService.createUserPet(userPet);
		return new ResponseEntity<UserPetModel>(userPetCreated, HttpStatus.OK);
	}
	
	@GetMapping("/list/{userName}")
	public ResponseEntity<List<UserPetModel>> getPetsByUserId(@PathVariable String userName) {
		List<UserPetModel> userPets = userPetService.getPetsByUserId(userName);
		return new ResponseEntity<List<UserPetModel>>(userPets, HttpStatus.OK); 
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateUserPet(@RequestBody UserPetModel userPet) {
		return new ResponseEntity<String>(userPetService.updateUserPet(userPet), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUserPet(@PathVariable Integer id) {
		return new ResponseEntity<String>(userPetService.deleteUserPet(id), HttpStatus.OK);
	}
	
}
