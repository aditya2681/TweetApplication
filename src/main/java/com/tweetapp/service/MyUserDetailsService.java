package com.tweetapp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tweetapp.entity.User;
import com.tweetapp.repository.UserRepository;

@Component
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userrepo.findById(username).get();

		return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(),
				new ArrayList<>());
	}

}
