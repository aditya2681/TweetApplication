package com.tweetapp.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document
@AllArgsConstructor
public class Tweet {
	
	private String tweet;
	private int likes;   
	private List<Replies> replies;
	

}
	