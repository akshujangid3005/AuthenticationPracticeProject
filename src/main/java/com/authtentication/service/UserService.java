package com.authtentication.service;

import java.util.List;

import com.authtentication.entity.User;


public interface UserService {
	
	public User createUser(User user,String url);
	
	public User updateUser(User user,Integer userId);
	
     public	User getUserById(Integer userId);
  	
  	List<User> getAllUsers();
  	
  	void deleteUser (Integer userId);

	


}
