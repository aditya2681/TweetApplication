package com.tweetapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.tweetapp.model.LoginCredentials;
import com.tweetapp.model.Passwordchange;
import com.tweetapp.model.Replies;
import com.tweetapp.model.UserToken;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.service.JwtUtil;
import com.tweetapp.service.MyUserDetailsService;
import com.tweetapp.service.SequenceGeneratorService;

@CrossOrigin("http://localhost:3000")
@RestController
public class LoginController {
	@Autowired
	MyUserDetailsService userDetailService;
	@Autowired
	UserRepository repo;
	@Autowired
	JwtUtil tokenUtil;
	@Autowired
	private SequenceGeneratorService  service;

	@PostMapping("/authenticate")
	public ResponseEntity<UserToken> login(@RequestBody LoginCredentials loginDetails) throws Exception {
		final UserDetails userDetails = userDetailService.loadUserByUsername(loginDetails.getEmailid());
		if (!(loginDetails.getPassword().equals(userDetails.getPassword())))
			throw new Exception("Invalid Credetnials");
		String generateToken = tokenUtil.generateToken(userDetails);
		return new ResponseEntity<>(new UserToken(loginDetails.getEmailid(), generateToken), HttpStatus.OK);
	}

	@GetMapping("/users")
	public String logisdn() {
//		final UserDetails userDetails = userDetailService.loadUserByUsername(loginDetails.getEmailid());
//		if (!(loginDetails.getPassword().equals(userDetails.getPassword())))
//			throw new Exception("Invalid Credetnials");
//		String generateToken = tokenUtil.generateToken(userDetails);
		User new1 = new User();
		new1.setEmailId("aditya.doredla@gmail.com");
		new1.setPassword("aditya");
		repo.save(new1);
		return "aditya";
	}

	@PostMapping("/changePassword")
	public ResponseEntity<User> changepassword(HttpServletRequest request, @RequestBody Passwordchange passwordchange)
			throws Exception {
		String emailId = tokenUtil.extractUsername(request.getHeader("Authorization").substring(7));
		Optional<User> user = repo.findById(emailId);
		User newUser = new User();
		if (user.isPresent()) {
			newUser = user.get();
			if (newUser.getPassword().equals(passwordchange.getOldPassword()))
				newUser.setPassword(passwordchange.getNewPassword());
			repo.save(newUser);
		}
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<User> reister(@RequestBody User user) throws Exception {
		User savedUser = repo.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}

	@Autowired
	private TweetRepository tweetrepo;
	
	@PostMapping("/tweet")
	public ResponseEntity<Tweet> savetweet(@RequestBody Tweet tweet) throws Exception {
//		String emailId = tokenUtil.extractUsername(request.getHeader("Authorization").substring(7));
		User user = repo.findById(tweet.getEmailId()).get();
		Map<String, String> reply = new HashMap<>();
		List<String>likes = new ArrayList<>();

		tweet.setReplies(reply);
		tweet.setLikes(likes);
		tweet.setId(service.getSequrnce(Tweet.SEQUENCE_NAME));

		Tweet t= tweetrepo.save(tweet);
		return new ResponseEntity<>(t, HttpStatus.OK);
	}

	@PutMapping("/replytweet/{tweetid}")
	public ResponseEntity<Tweet> replytweet(@PathVariable int tweetid,@RequestBody Replies reply,HttpServletRequest request) throws Exception {
		User user = repo.findById(reply.getEmailId()).get();
		Tweet t = tweetrepo.findById(tweetid).get();
		 t.getReplies().put(reply.getEmailId().replace('.','_'),reply.getReply());
		 tweetrepo.save(t);
		return new ResponseEntity<>(t, HttpStatus.OK);
	}

	@PutMapping("/tweet/{id}")
	public ResponseEntity<Tweet> updateTweet(@PathVariable int id, @RequestBody Tweet tweet)
			throws Exception {
//		String emailId = tokenUtil.extractUsername(tweet.getEmailId());
//		User user = repo.findById(emailId).get();
		Tweet tweet1= tweetrepo.findById(id).get();
		Tweet tweet2= new Tweet();
		if(tweet1.getEmailId().equals(tweet.getEmailId())) {
			tweet1.setTweet(tweet.getTweet());
			tweetrepo.save(tweet1);
		}
		return new ResponseEntity<>(tweet2, HttpStatus.OK);
	}

//	@DeleteMapping("/deleltetweet/{id}")
//	public ResponseEntity<User> deleteTweet(@PathVariable int id, @RequestBody Tweet tweet, HttpServletRequest request)
//			throws Exception {
//		String emailId = tokenUtil.extractUsername(request.getHeader("Authorization").substring(7));
//		User user = repo.findById(emailId).get();
//		user.getTweets().remove(id);
//		User savedUser = repo.save(user);
//		return new ResponseEntity<>(savedUser, HttpStatus.OK);
//	}

//	@GetMapping("/logout")
//	public ResponseEntity<User> logout(HttpServletRequest request) throws Exception {
//		String emailId = tokenUtil.extractUsexername(request.getHeader("Authorization").substring(7));
//		tokenUtil.is
//		return new ResponseEntity<>(savedUser, HttpStatus.OK);
//	}

	@GetMapping("/home")
	public ResponseEntity<String> homepage() throws Exception {
		return new ResponseEntity<>("xyz", HttpStatus.OK);
	}
}
