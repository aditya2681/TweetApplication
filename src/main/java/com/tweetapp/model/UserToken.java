package com.tweetapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserToken {
	
	public UserToken(String emailid, String jwt) {
		super();
		this.emailid = emailid;
		this.jwt = jwt;
	}

	private String emailid;
	
	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	private String jwt;

}
