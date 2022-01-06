package com.tweetapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.entity.Tweet;
import com.tweetapp.entity.User;
import com.tweetapp.model.Likes;
import com.tweetapp.model.LoginCredentials;
import com.tweetapp.model.Password;
import com.tweetapp.model.Replies;
import com.tweetapp.model.TweetText;
import com.tweetapp.model.UserCreatedResposne;
import com.tweetapp.model.UserToken;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.service.MyUserDetailsService;
import com.tweetapp.service.TweetService;
import com.tweetapp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:3000")
@RestController
@Slf4j
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MyUserDetailsService userDetailService;
	@Autowired
	UserRepository repo;

	
	@Autowired
	private TweetService tweetService;
	
	
	
	
	
	
	
	
	
	@PostMapping("/register")
	public ResponseEntity<?> reister(@RequestBody User user) throws Exception {
	
		if(repo.findById(user.getEmailId()).isPresent()) {
			return new ResponseEntity<>(new UserCreatedResposne("Email Address already exists"), HttpStatus.OK);
		}
		User savedUser = repo.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	
	
	}
	

	@PostMapping("/authenticate")
	public ResponseEntity<UserToken> login(@RequestBody LoginCredentials loginDetails) throws Exception {
		String generateToken = null;


		try {

		final UserDetails userDetails = userDetailService.loadUserByUsername(loginDetails.getEmailid());


		if (!(loginDetails.getPassword().equals(userDetails.getPassword())))
			throw new Exception("Invalid Credetnials");

		}catch(Exception e ) {
			if(e.getMessage().equals("Invalid Credetnials"))
			{

				return new ResponseEntity<>(new UserToken("Password is Incorrect"), HttpStatus.OK);
			}

			if(e.getMessage().equals("No value present"))
			{

				return  new ResponseEntity<>(new UserToken("Username or Password Doesn't exist"), HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(new UserToken(loginDetails.getEmailid(), generateToken), HttpStatus.OK);
	}
	

	@GetMapping("/getAllTweets")
	public List<Tweet> getAllTweets() {
//		log.info("Getting A÷÷llTweets");
		List<Tweet> findAll = tweetService.getAllTweets();
//		log.info("Retrived All Users succesfully");÷
		return findAll;
	}

	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {
//		log.info("API call for getAllthe users");
		List<User> findAll = userService.getAllUsers();
//		log.info("Succesfully retrived the users");
		return findAll;
	}

	

	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody Password password) throws Exception {
//		log.info("API CALL->Change password");
		return userService.changePassword(password);
		
	}

	

	@PostMapping("/tweet")
	public ResponseEntity<?> savetweet(@RequestBody Tweet tweet) throws Exception {
//		log.info("API call->add tweet");
		return tweetService.addTweet(tweet);
		

	}

	@PutMapping("/replytweet/{tweetid}")
	public ResponseEntity<Tweet> replytweet(@PathVariable int tweetid, @RequestBody Replies reply,
			HttpServletRequest request) throws Exception {
//		log.info("API call->add reply to tweet");
		return tweetService.handleReply(tweetid,reply);
		
	}

	@PutMapping("/updatetweet/{id}")
	public ResponseEntity<?> updateTweet(@PathVariable int id, @RequestBody TweetText tweet) throws Exception {
//		log.info("API call->Update tweet");
	return	tweetService.handleUpdate( id,tweet);
		
	}

	@PutMapping("/liketweet/{id}")
	public ResponseEntity<Tweet> updateLikes(@PathVariable int id, @RequestBody Likes like) throws Exception {
//		log.info("API call->like tweet");
		return tweetService.handleLike(id,like);
		
	}

	@DeleteMapping("/deleltetweet/{id}")
	public ResponseEntity<?> deleteTweet(@PathVariable int id) throws Exception {
//		log.info("API call->delete tweet");
		tweetService.deleteTweet(id);
		return new ResponseEntity<>(tweetService.getAllTweets(), HttpStatus.OK);
	}
	
	

	
}
