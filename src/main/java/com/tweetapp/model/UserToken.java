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

	public UserToken(String errormessage) {
		super();
		this.errormessage = errormessage;
	}

	private String emailid;
	private String errormessage;
	
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

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	private String jwt;

}
