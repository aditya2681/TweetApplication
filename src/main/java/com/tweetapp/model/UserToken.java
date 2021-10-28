package com.tweetapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserToken {
	
	private String emailid;
	
	private String jwt;

}
