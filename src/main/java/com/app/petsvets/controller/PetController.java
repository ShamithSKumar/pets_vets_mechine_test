package com.app.petsvets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.petsvets.entity.Pet;
import com.app.petsvets.service.PetService;

@RestController
@RequestMapping("/pets")
public class PetController {

	@Autowired
	private PetService petService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Pet>> getPetList() {
		List<Pet> pets = petService.getPetList(); 
		return new ResponseEntity<List<Pet>>(pets, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
		Pet petCreated = petService.CreatePet(pet);
		return new ResponseEntity<Pet>(petCreated, HttpStatus.OK);
	}
}
