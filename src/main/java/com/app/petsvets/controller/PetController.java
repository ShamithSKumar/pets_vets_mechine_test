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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pet")
public class PetController {

	@Autowired
	private PetService petService;

	/**
	 * To get all available pets
	 * 
	 * @return List of pets
	 */
	@GetMapping("/list")
	public ResponseEntity<List<Pet>> getPetList() {
		log.info("Enabled getPetList endpoint");
		return new ResponseEntity<List<Pet>>(petService.getPetList(), HttpStatus.OK);
	}

	/**
	 * To create new pets
	 * 
	 * @param pet pet details as requestBody
	 * @return Pet created pet details
	 */
	@PostMapping("/create")
	public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
		log.info("Enabled create petr endpoint");
		Pet petCreated = petService.CreatePet(pet);
		return new ResponseEntity<Pet>(petCreated, HttpStatus.OK);
	}
}
