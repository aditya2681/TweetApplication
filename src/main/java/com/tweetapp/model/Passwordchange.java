package com.tweetapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Passwordchange {

	private String oldPassword;
	private String newPassword;
}
