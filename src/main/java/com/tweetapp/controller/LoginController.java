package com.tweetapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.tweetapp.service.JwtUtil;
import com.tweetapp.service.MyUserDetailsService;
import com.tweetapp.service.SequenceGeneratorService;
import com.tweetapp.service.TweetService;
import com.tweetapp.service.UserService;

@CrossOrigin("http://localhost:3000")
@RestController
public class LoginController {
	@Autowired
	MyUserDetailsService userDetailService;
	@Autowired
	UserService userService;
	@Autowired
	JwtUtil tokenUtil;
	@Autowired
	private SequenceGeneratorService service;
	
	@Autowired
	private TweetService tweetService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> login(@RequestBody LoginCredentials loginDetails) throws Exception {
		return userService.validateLogin(loginDetails);
	}

	@GetMapping("/users")
	public String logisdn() {
		User new1 = new User();
		new1.setEmailId("aditya.doredla@gmail.com");
		new1.setPassword("aditya");
		
		userService.saveUSer(new1);
		return "aditya";
	}

	@GetMapping("/getAllTweets")
	public List<Tweet> getAllTweets() {
		List<Tweet> findAll = tweetService.getAllTweets();
		return findAll;
	}

	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {

		List<User> findAll = userService.getAllUsers();
		return findAll;
	}

	@PostMapping("/register")
	public ResponseEntity<?> reister(@RequestBody User user) throws Exception {
		if (userService.findUserById(user.getEmailId()).isPresent()) {
			return new ResponseEntity<>(new UserCreatedResposne("Email Address already exists"), HttpStatus.OK);
		}
		User savedUser = userService.saveUSer(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}

	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody Password password) throws Exception {
		return userService.changePassword(password);
		
	}

	

	@PostMapping("/tweet")
	public ResponseEntity<?> savetweet(@RequestBody Tweet tweet) throws Exception {
		return tweetService.addTweet(tweet);
		

	}

	@PutMapping("/replytweet/{tweetid}")
	public ResponseEntity<Tweet> replytweet(@PathVariable int tweetid, @RequestBody Replies reply,
			HttpServletRequest request) throws Exception {
		return tweetService.handleReply(tweetid,reply);
		
	}

	@PutMapping("/updatetweet/{id}")
	public ResponseEntity<?> updateTweet(@PathVariable int id, @RequestBody TweetText tweet) throws Exception {
	return	tweetService.handleUpdate( id,tweet);
		
	}

	@PutMapping("/liketweet/{id}")
	public ResponseEntity<Tweet> updateLikes(@PathVariable int id, @RequestBody Likes like) throws Exception {
		return tweetService.handleLike(id,like);
		
	}

	@DeleteMapping("/deleltetweet/{id}")
	public ResponseEntity<?> deleteTweet(@PathVariable int id) throws Exception {
		
		tweetService.deleteTweet(id);
		return new ResponseEntity<>(tweetService.getAllTweets(), HttpStatus.OK);
	}

	
}
