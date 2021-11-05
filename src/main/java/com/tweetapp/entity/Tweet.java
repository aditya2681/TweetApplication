package com.tweetapp.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document
@AllArgsConstructor
public class Tweet {
	

	@Transient
    public static final String SEQUENCE_NAME = "tweet_sequence";
	
	@Id
	private int id;
	
	private String emailId;
	
	private String tweet;
	private List<String> likes;   
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	
	public List<String>  getLikes() {
		return likes;
	}
	public void setLikes(List<String> likes) {
		this.likes = likes;
	}
	public Map<String, String> getReplies() {
		return replies;
	}
	public void setReplies(Map<String, String> replies) {
		this.replies = replies;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private Map<String, String> replies;
	

}
	