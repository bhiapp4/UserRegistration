package com.jnit.registration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jnit.registration.dao.UserDao;
import com.jnit.registration.model.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> getAllUsers() throws Exception{
		return userDao.loadAllUsers();
	}

	@Override
	public User createUser(User user) throws Exception{
		return userDao.persistUser(user);
	}

	@Override
	public User updateUser(User user) throws Exception{
		return userDao.updateUser(user);
	}

	@Override
	public int deleteUser(Long id) throws Exception{
		return userDao.deleteUser(id);
	}

}
