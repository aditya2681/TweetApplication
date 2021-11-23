package com.tweetapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tweetapp.entity.User;
import com.tweetapp.model.LoginCredentials;
import com.tweetapp.model.Password;
import com.tweetapp.model.UserCreatedResposne;
import com.tweetapp.model.UserToken;
import com.tweetapp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	 MyUserDetailsService userDetailService;
	
	
	@Autowired
	 JwtUtil tokenUtil;
	
	@Autowired
	UserRepository repo;

	public User saveUSer(User new1) {
		return repo.save(new1);
		// TODO Auto-generated method stub
		
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	public Optional<User> findUserById(String emailId) {
		// TODO Auto-generated method stub
		return repo.findById(emailId);
	}

	public  ResponseEntity<?> validateLogin(LoginCredentials loginDetails) {
		// TODO Auto-generated method stub
		String generateToken = null;

		try {
			final UserDetails userDetails = userDetailService.loadUserByUsername(loginDetails.getEmailid());
			if (!(loginDetails.getPassword().equals(userDetails.getPassword())))
				throw new Exception("Invalid Credetnials");
			generateToken = tokenUtil.generateToken(userDetails);
		} catch (Exception e) {
			if (e.getMessage().equals("Invalid Credetnials")) {
				return new ResponseEntity<>(new UserToken("Password is Incorrect"), HttpStatus.OK);
			}
			if (e.getMessage().equals("No value present")) {

				return new ResponseEntity<>(new UserToken("Username or Password Doesn't exist"), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new UserToken(loginDetails.getEmailid(), generateToken), HttpStatus.OK);
	
		
	}

	public ResponseEntity<?> changePassword(Password password) {
		User user = this.findUserById(password.getEmailId()).get();
		if (user.getPassword().equals(password.getOldPassword())) {
			user.setPassword(password.getNewPassword());
			User updateUser = this.saveUSer(user);
			return new ResponseEntity<>(new UserCreatedResposne("Password Changed Succesfully"), HttpStatus.OK);

		}
		return new ResponseEntity<>(new UserCreatedResposne("Your Old Password is Incorrect"), HttpStatus.OK);

	}

}
