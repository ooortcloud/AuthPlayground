package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.service.UserDao;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	@Override
	public int addUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return userDao.addUser(user);
	}

	@Override
	public List<User> getUserListById(String userId) throws Exception {
		// TODO Auto-generated method stub
		return userDao.getUserListById(userId);
	}

	@Override
	public List<User> getUserListByNickname(String nickname) throws Exception {
		// TODO Auto-generated method stub
		return userDao.getUserListByNickname(nickname);
	}

	@Override
	public List<User> getUserList() throws Exception {
		// TODO Auto-generated method stub
		return userDao.getUserList();
	}

	@Override
	public int updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return userDao.updateUser(user);
	}

	@Override
	public User getUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return userDao.getUser(user);
	}

	
}
