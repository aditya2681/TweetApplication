package com.tweetapp.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tweetapp.model.Replies;

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
	
	private String date;
	
	private String time;
	
	
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
	public List<Replies> getReplies() {
		return replies;
	}
	public void setReplies(List<Replies> replies) {
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	private List<Replies> replies;
	
	private List<String> tags;
	

}
	