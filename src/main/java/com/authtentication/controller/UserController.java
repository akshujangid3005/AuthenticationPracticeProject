package com.authtentication.controller;

import java.security.Principal;
import java.util.List;

import com.authtentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.authtentication.entity.ApiResponse;
import com.authtentication.entity.User;
import com.authtentication.service.UserService;

import jakarta.validation.Valid;





@Controller
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;



	
	
	@PostMapping("/user")
	public ResponseEntity<User> createUser( @Valid @RequestBody User user){
		
		User createUser =this.userService.createUser(user, null );
	    return new ResponseEntity<>(createUser,HttpStatus.CREATED);
	
	}
	
	@PutMapping("/user/{userId}")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user,@PathVariable Integer userId){
	User  updatedUser=	this.userService.updateUser(user, userId);
	return  ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
	   	this.userService.deleteUser(userId);
	   	return new ResponseEntity<ApiResponse>( new ApiResponse("User Deleted Succsessfully",true),HttpStatus.OK);
	}
	//GET - get users
		@GetMapping("/user")
		public ResponseEntity <List<User>> getAllUsers(){
			return ResponseEntity.ok(this.userService.getAllUsers());
			
		}
		// get by Id users
		@GetMapping("/user/{userId}")
		public ResponseEntity <User> getUserByUserId(@PathVariable Integer userId){
			return ResponseEntity.ok(this.userService.getUserById(userId));
		
		
	}

		@ModelAttribute
		public void commonProfile(Principal principal, Model m) {
			if (principal != null) {
				String email = principal.getName();
				User user = userRepository.findByUserEmail(email);
				m.addAttribute("user", user);
			}
		}

		@GetMapping("/user/profile")
		public String profile() {
			return "User/profile"; // Thymeleaf will resolve this to profile.html
		}
		

		@GetMapping("/user/index")
		public String userdashbord(Model m , Principal p) {
			String userName=p.getName();
		//	System.out.println(" UserName"+ userName);
			
		User user =	userRepository.findByUserEmail(userName);
		//System.out.println(user);
			
			
			return "User/userindex"; 
		}
	   
		@GetMapping("/user/setting")
		public String openSetting() {
			
			return "User/setting";
		}
		
		@PostMapping("/user/change-password")
		public String changePassword(@RequestParam("oldpassword")String oldpassword,@RequestParam("newpassword")String newpassword,Principal principal) {
			System.out.println("oldpassword "+ oldpassword);
			System.out.println("newpassword "+ newpassword);
			
			String userName=principal.getName();
	   User currentUser=this.userRepository.findByUserEmail(userName);
	   System.out.println(currentUser.getUserPassword());
	   
	   if (this.bCryptPasswordEncoder.matches(oldpassword, currentUser.getUserPassword())) {
		   currentUser.setUserPassword(this.bCryptPasswordEncoder.encode(newpassword));
		   this.userRepository.save(currentUser);
		   // change the password
		
	}else {
		
	}
	   
			
			return "User/userindex";
		}
		
		
	

}
