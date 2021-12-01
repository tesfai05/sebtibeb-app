package com.tesfai.sebtibeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tesfai.sebtibeb.entity.User;
import com.tesfai.sebtibeb.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User user) {
		
		return userRepository.save(user);
	}

	@Override
	public User checkUserAvailability(String username) {
		
		return userRepository.findUserByUsername(username);
	}

}
