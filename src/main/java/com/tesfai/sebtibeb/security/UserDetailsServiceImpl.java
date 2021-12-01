package com.tesfai.sebtibeb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tesfai.sebtibeb.entity.User;
import com.tesfai.sebtibeb.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("Username or password is incorrect !!" );
		}
		
		return new SebtibebUserDetails(user);
	}

}
