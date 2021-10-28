package com.tweetapp.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
		User savedUser = repo.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}

	@GetMapping("/home")
	public ResponseEntity<String> homepage() throws Exception {
		return new ResponseEntity<>("xyz", HttpStatus.OK);
	}
}
