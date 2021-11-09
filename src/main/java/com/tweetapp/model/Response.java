package com.tweetapp.model;

public class Response {
	private String Errormessage;

	

	public Response(String errormessage) {
		super();
		Errormessage = errormessage;
	}

	public String getErrormessage() {
		return Errormessage;
	}

	public void setErrormessage(String errormessage) {
		Errormessage = errormessage;
	}

}
