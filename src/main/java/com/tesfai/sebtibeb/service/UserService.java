package com.tesfai.sebtibeb.service;

import com.tesfai.sebtibeb.entity.User;

public interface UserService {
	public User save(User user);

	public User checkUserAvailability(String username);
}
