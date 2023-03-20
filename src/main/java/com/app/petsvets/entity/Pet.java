package com.app.petsvets.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "pet")
public class Pet {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "pet_id", nullable = false)
	private Integer petId;
	private String petType;
	@OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
	private List<UserPet> userPets;
}
