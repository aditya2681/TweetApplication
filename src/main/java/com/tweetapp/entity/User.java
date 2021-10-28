package com.tweetapp.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	private String emailId;
	private String firstName;
	private String lastName;
	private String gender;
	private LocalDate birthDate;
	private String password;
	
}
