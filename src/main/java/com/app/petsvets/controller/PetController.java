package com.app.petsvets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.petsvets.model.PetModel;
import com.app.petsvets.model.ResponseModel;
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
	@ResponseBody
	@GetMapping("/list")
	public ResponseEntity<ResponseModel> getPetList() {
		log.info("Enabled getPetList endpoint");
		ResponseModel result = new ResponseModel();
		result.setData(petService.getPetList());
		result.setMessage("Pet list fetched successfully");
		result.setStatus(true);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.OK);
	}

	/**
	 * To create new pets
	 * 
	 * @param pet pet details as requestBody
	 * @return Pet created pet details
	 */
	@ResponseBody
	@PostMapping("/create")
	public ResponseEntity<ResponseModel> createPet(@RequestBody PetModel pet) {
		log.info("Enabled create petr endpoint");
		ResponseModel result = new ResponseModel();
		result.setData(petService.createPet(pet));
		result.setMessage("Pet created successfully");
		result.setStatus(true);
		return new ResponseEntity<ResponseModel>(result, HttpStatus.OK);
	}
}
