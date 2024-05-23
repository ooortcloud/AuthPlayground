package com.example.demo.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.User;

@Mapper
public interface UserDao {

	public int addUser(User user) throws Exception;
	
	public List<User> getUserListById(String userId) throws Exception;
	
	public List<User> getUserListByNickname(String nickname) throws Exception;
	
	public List<User> getUserList() throws Exception;
	
	public int updateUser(User user) throws Exception;
	
	public User getUser(User user) throws Exception;
}
