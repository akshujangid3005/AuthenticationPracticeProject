package com.authtentication.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.authtentication.entity.User;



public interface UserRepository  extends JpaRepository<User, Integer>{
	
	
	  User findByUserEmail(String userEmail);
	  
	  User findByVarificationCode(String VarificationCode);

	

}
