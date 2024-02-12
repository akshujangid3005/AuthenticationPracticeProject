package com.authtentication.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.authtentication.Exception.ResourceNotFoundException;
import com.authtentication.entity.User;
import com.authtentication.repository.UserRepository;
import com.authtentication.service.UserService;




@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

     @Override
	public User createUser(User user,String url) {
		
			String password=bCryptPasswordEncoder.encode(user.getUserPassword());
			user.setUserPassword(password);
			user.setRole("ROLE_USER");
			user.setUserenable(false);
			user.setVarificationCode(UUID.randomUUID().toString());
		   User  savedUser =userRepository.save(user);
		   
		   if (savedUser!=null) {
			sendVerificationEmail(savedUser, url);
		}
		   
		   
		   
		    return savedUser;
	}

	@Override
	public User updateUser(User user, Integer userId) {
		
		User user2= this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		    
		user2.setUserName(user.getUserName());
		user2.setUserPassword(user.getUserPassword());
		User updatedUser =this.userRepository.save(user2);
		return (updatedUser);
	}

	@Override
	public User getUserById(Integer userId) {
		User user= this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users  =	this.userRepository.findAll();
		return users;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user= this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		    this.userRepository.delete(user);
		
	}
	
	private void sendVerificationEmail(User user, String url) {
		String subject = "Account Verification";
	    String message = "<p>Hi " + user.getUserName() + ",</p>"
	            + "<p>Thanks for registering! Please click the following link to verify your account:</p>"
	            + "<p><a href='" + url + "/auth/verify-email?code=" + user.getVarificationCode() + "'>Verify Account</a></p>"
	            + "<p>Thanks,</p>"
	            + "<p>The Authentication Team</p>";
	    boolean sent = emailServiceImpl.sendEmail(subject, message, user.getUserEmail());
	    if (sent) {
	        System.out.println("Email verification url sent successfully.");
	    } else {
	        System.out.println("Error in sending email.");
	    }
	}
	
	
	
	
	public boolean verifyAccount(String varificationCode) {
		
		User user=userRepository.findByVarificationCode(varificationCode);
		if (user==null) {
			return false;
			
		}else {
			user.setUserenable(true);
			user.setVarificationCode(null);
			userRepository.save(user);
			return true;

		}
			
		
		
		
	}
}




