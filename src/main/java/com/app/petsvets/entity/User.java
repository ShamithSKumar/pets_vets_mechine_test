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

@Data
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	private String userName;
	private String email;
	private String phone;
	private String password;
	private String role;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserPet> userPets;
	
}
