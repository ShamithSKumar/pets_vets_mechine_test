package com.app.petsvets.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.petsvets.entity.Pet;
import com.app.petsvets.model.ResponseModel;
import com.app.petsvets.service.PetService;


@WebMvcTest(PetController.class)
public class PetControllerTest {

//	@Autowired
//	private MockMvc mockMvc;
//	
//	@MockBean
//	private PetService petService;
//	
//	@Test
//	public void getPetList_test() throws Exception {
//		ResponseModel result = new ResponseModel();
//		List<Pet> pets = new ArrayList<>();
//		Pet pet = new Pet();
//		pet.setPetId(1);
//		pet.setPetType("Dog");
//		pets.add(pet);
//		result.setData(pets);
//		result.setMessage("Success");
//		result.setStatus(true);
//		Mockito.when(petService.getPetList()).thenReturn(pets);
//		mockMvc.perform(get("/list")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(1)))
//		.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("Success")));
//	}
}
