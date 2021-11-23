package com.tweetapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tweetapp.entity.Tweet;
import com.tweetapp.entity.User;
import com.tweetapp.model.Likes;
import com.tweetapp.model.Replies;
import com.tweetapp.model.TweetText;
import com.tweetapp.repository.TweetRepository;

@Service
public class TweetService {
	
	
	@Autowired
	private TweetRepository tweetrepository;
	
	
	public Tweet savetweet(Tweet tweet){
		return tweetrepository.save(tweet);
	
	}
	
	
	public Tweet findTweetById(int id){
		return tweetrepository.findById(id).get();
		
	}
	
	public List<Tweet> getAllTweets(){
		return tweetrepository.findAll();
	}


	public void deleteTweet(int id) {
		tweetrepository.deleteById(id);
		// TODO Auto-generated method stub
		
	}
	@Autowired
	private SequenceGeneratorService service;
	
	@Autowired
	private UserService userService;

	public ResponseEntity<?> addTweet(Tweet tweet) {
		try {
			User user = userService.findUserById(tweet.getEmailId()).get();
			List<Replies> reply = new ArrayList<>();
			List<String> likes = new ArrayList<>();

			tweet.setReplies(reply);
			tweet.setLikes(likes);
			tweet.setId(service.getSequrnce(Tweet.SEQUENCE_NAME));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			
			tweet.setDate(LocalDate.now().toString());
			tweet.setTime(formatter.format(LocalDateTime.now()));
	           
			Tweet t = this.savetweet(tweet);
			return new ResponseEntity<>(this.getAllTweets(), HttpStatus.OK);
		} catch (Exception e) {

		}
		return null;
	}


	public ResponseEntity<Tweet> handleLike(int id, Likes like) {
		Tweet tweet1 = this.findTweetById(id);
		if (tweet1.getLikes().contains(like.getLike())) {
			tweet1.getLikes().remove(like.getLike());
		} else {
			tweet1.getLikes().add(like.getLike());
		}
		Tweet save = this.savetweet(tweet1);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}


	public ResponseEntity<?> handleUpdate(int id, TweetText tweet) {
		Tweet tweet1 = this.findTweetById(id);
		List<Tweet> tweet2= new ArrayList<>() ;
		
		if (tweet1.getEmailId().equals(tweet.getEmailId())) {
			tweet1.setTweet(tweet.getTweet());
			this.savetweet(tweet1);
			tweet2=this.getAllTweets();
		}
		return new ResponseEntity<>(tweet2, HttpStatus.OK);
	}


	public ResponseEntity<Tweet> handleReply(int tweetid, Replies reply) {
		User user = userService.findUserById(reply.getEmailId()).get();
		Tweet t = this.findTweetById(tweetid);
		t.getReplies().add(reply);
		this.savetweet(t);
		return new ResponseEntity<>(t, HttpStatus.OK);
	}

}
