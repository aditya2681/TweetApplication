package com.tweetapp.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {

	@Id
	private String emailId;
	private String firstName;
	private String lastName;
	private String gender;
	private LocalDate birthDate;
	private String password;
	private List<Tweet> tweets;
	
}
