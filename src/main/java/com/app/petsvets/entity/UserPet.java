package com.app.petsvets.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "userpet")
public class UserPet {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer userpetId;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user", referencedColumnName = "user_id")
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pet", referencedColumnName = "pet_id")
	private Pet pet;
	private Integer amount;
	private String name;
}
