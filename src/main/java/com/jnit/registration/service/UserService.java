package com.jnit.registration.service;

import java.util.List;

import com.jnit.registration.model.User;

public interface UserService {

	public List<User> getAllUsers() throws Exception;
	
	public User createUser(User user) throws Exception;
	
	public User updateUser(User user) throws Exception;
	
	public int deleteUser(Long id) throws Exception;

}
