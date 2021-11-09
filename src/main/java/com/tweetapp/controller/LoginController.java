package com.tweetapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import com.tweetapp.model.Passwordchange;
import com.tweetapp.model.Replies;
import com.tweetapp.model.Response;
import com.tweetapp.model.UserCreatedResposne;
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
	public ResponseEntity<?> login(@RequestBody LoginCredentials loginDetails) throws Exception {
		String generateToken = null;
		
		
		try {
			
		final UserDetails userDetails = userDetailService.loadUserByUsername(loginDetails.getEmailid());
		
		
		if (!(loginDetails.getPassword().equals(userDetails.getPassword())))
			throw new Exception("Invalid Credetnials");
		 generateToken = tokenUtil.generateToken(userDetails);
		
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
	
	@GetMapping("/getAllTweets")
	public List<Tweet> getAllTweets() {
//		final UserDetails userDetails = userDetailService.loadUserByUsername(loginDetails.getEmailid());
//		if (!(loginDetails.getPassword().equals(userDetails.getPassword())))
//			throw new Exception("Invalid Credetnials");
//		String generateToken = tokenUtil.generateToken(userDetails);
		List<Tweet> findAll = tweetrepo.findAll();
		return findAll;
	}
	
	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {
//		final UserDetails userDetails = userDetailService.loadUserByUsername(loginDetails.getEmailid());
//		if (!(loginDetails.getPassword().equals(userDetails.getPassword())))
//			throw new Exception("Invalid Credetnials");
//		String generateToken = tokenUtil.generateToken(userDetails);
		List<User> findAll = repo.findAll();
		return findAll;
	}
	
	
//	@GetMapping("/getMyTweets/{id}")
//	public List<?> getMyTweets(@PathVariable String id) {
////		final UserDetails userDetails = userDetailService.loadUserByUsername(loginDetails.getEmailid());
////		if (!(loginDetails.getPassword().equals(userDetails.getPassword())))
////			throw new Exception("Invalid Credetnials");
////		String generateToken = tokenUtil.generateToken(userDetails);
//		List<Tweet> findAll = tweetrepo.findByEmailId();
//		return findAll;
//	}
//	
	

//	@PostMapping("/changePassword")
//	public ResponseEntity<User> changepassword(HttpServletRequest request, @RequestBody Passwordchange passwordchange)
//			throws Exception {
//		String emailId = tokenUtil.extractUsername(request.getHeader("Authorization").substring(7));
//		Optional<User> user = repo.findById(emailId);
//		User newUser = new User();
//		if (user.isPresent()) {
//			newUser = user.get();
//			if (newUser.getPassword().equals(passwordchange.getOldPassword()))
//				newUser.setPassword(passwordchange.getNewPassword());
//			repo.save(newUser);
//		}
//		return new ResponseEntity<>(newUser, HttpStatus.OK);
//	}

	@PostMapping("/register")
	public ResponseEntity<?> reister(@RequestBody User user) throws Exception {
		if(repo.findById(user.getEmailId()).isPresent() ) {
			return new ResponseEntity<>(new UserCreatedResposne("Email Address already exists"), HttpStatus.OK);
		}
		User savedUser = repo.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}

	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody Password password) throws Exception {
		User user = repo.findById(password.getEmailId()).get();
		if(user.getPassword().equals(password.getOldPassword()) ) {
			user.setPassword(password.getNewPassword());
			User updateUser = repo.save(user);
		return new ResponseEntity<>(new UserCreatedResposne("Password Changed Succesfully"), HttpStatus.OK);
			
			}
		return new ResponseEntity<>(new UserCreatedResposne("Your Old Password is Incorrect"), HttpStatus.OK);
		
	}

	@Autowired
	private TweetRepository tweetrepo;
	
	@PostMapping("/tweet")
	public ResponseEntity<?> savetweet(@RequestBody Tweet tweet) throws Exception {
//		String emailId = tokenUtil.extractUsername(request.getHeader("Authorization").substring(7));
		try {
			
		
		User user = repo.findById(tweet.getEmailId()).get();
		List<Replies>reply = new ArrayList<>();
		List<String>likes = new ArrayList<>();

		tweet.setReplies(reply);
		tweet.setLikes(likes);
		tweet.setId(service.getSequrnce(Tweet.SEQUENCE_NAME));

		Tweet t= tweetrepo.save(tweet);
		return new ResponseEntity<>(tweetrepo.findAll(), HttpStatus.OK);
		}catch(Exception e) {
			
		}
		return null;
		
	}

	@PutMapping("/replytweet/{tweetid}")
	public ResponseEntity<Tweet> replytweet(@PathVariable int tweetid,@RequestBody Replies reply,HttpServletRequest request) throws Exception {
		User user = repo.findById(reply.getEmailId()).get();
		Tweet t = tweetrepo.findById(tweetid).get();
		 t.getReplies().add(reply);
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
	
	
	@PutMapping("/liketweet/{id}")
	public ResponseEntity<Tweet> updateLikes(@PathVariable int id, @RequestBody Likes like)
			throws Exception {
		Tweet tweet1= tweetrepo.findById(id).get();
		if(tweet1.getLikes().contains(like.getLike())) {
			tweet1.getLikes().remove(like.getLike());
		}else {
			tweet1.getLikes().add(like.getLike());
		}
		Tweet save = tweetrepo.save(tweet1);
		return new ResponseEntity<>(save, HttpStatus.OK);
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
