package com.tweetapp.model;

import lombok.Data;

@Data
public class Likes {
	
	private String like;

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}





}
