package com.tweetapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.entity.Replies;
import com.tweetapp.entity.Tweet;
import com.tweetapp.entity.User;
import com.tweetapp.model.LoginCredentials;
import com.tweetapp.model.Passwordchange;
import com.tweetapp.model.UserToken;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.service.JwtUtil;
import com.tweetapp.service.MyUserDetailsService;

@RestController
public class LoginController {
	@Autowired
	MyUserDetailsService userDetailService;
	@Autowired
	UserRepository repo;
	@Autowired
	JwtUtil tokenUtil;

	@PostMapping("/authenticate")
	public ResponseEntity<UserToken> login(@RequestBody LoginCredentials loginDetails) throws Exception {
		final UserDetails userDetails = userDetailService.loadUserByUsername(loginDetails.getEmailid());
		if (!(loginDetails.getPassword().equals(userDetails.getPassword())))
			throw new Exception("Invalid Credetnials");
		String generateToken = tokenUtil.generateToken(userDetails);
		return new ResponseEntity<>(new UserToken(loginDetails.getEmailid(), generateToken), HttpStatus.OK);
	}

	@PostMapping("/changePassword")
	public ResponseEntity<User> changepassword(HttpServletRequest request,@RequestBody Passwordchange passwordchange) throws Exception {
		String emailId = tokenUtil.extractUsername(request.getHeader("Authorization").substring(7));
		Optional<User> user = repo.findById(emailId);
		User newUser = new User();
		if(user.isPresent()) {
			 newUser = user.get();
			if(newUser.getPassword().equals(passwordchange.getOldPassword()))
				newUser.setPassword(passwordchange.getNewPassword());
			repo.save(newUser);
		}
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<User> reister(@RequestBody User user) throws Exception {
		List tweetlist = new ArrayList<>();
		user.setTweets(tweetlist);
		User savedUser = repo.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}
	

	@PostMapping("/tweet")
	public ResponseEntity<User> savetweet(@RequestBody Tweet tweet,HttpServletRequest request) throws Exception {
		String emailId = tokenUtil.extractUsername(request.getHeader("Authorization").substring(7));
		User user = repo.findById(emailId).get();
		List<Replies> reply = new ArrayList<>();
		tweet.setReplies(reply);
		   user.getTweets().add(tweet);
		User savedUser = repo.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}
	
	
	
	@PutMapping("/replytweet/{tweetid}")
	public ResponseEntity<User> replytweet(@PathVariable int tweetid,@RequestBody Replies reply,HttpServletRequest request) throws Exception {
		String emailId = tokenUtil.extractUsername(request.getHeader("Authorization").substring(7));
		User user = repo.findById(emailId).get();
		reply.setFirstName(user.getFirstName());
		   user.getTweets().get(tweetid).getReplies().add(reply);
		User savedUser = repo.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}
	
	
	@PutMapping("/tweet/{id}")
	public ResponseEntity<User> updateTweet(@PathVariable int id,@RequestBody Tweet tweet,HttpServletRequest request) throws Exception {
		String emailId = tokenUtil.extractUsername(request.getHeader("Authorization").substring(7));
		User user = repo.findById(emailId).get();
		   user.getTweets().set(id, tweet);
		User savedUser = repo.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleltetweet/{id}")
	public ResponseEntity<User> deleteTweet(@PathVariable int id,@RequestBody Tweet tweet,HttpServletRequest request) throws Exception {
		String emailId = tokenUtil.extractUsername(request.getHeader("Authorization").substring(7));
		User user = repo.findById(emailId).get();
		   user.getTweets().remove(id);
		User savedUser = repo.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}
	
	
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
