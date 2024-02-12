package com.authtentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.authtentication.entity.Message;
import com.authtentication.entity.User;
import com.authtentication.repository.UserRepository;
import com.authtentication.service.UserService;
import com.authtentication.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping()
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository repository;

	@Autowired
	private  UserServiceImpl userServiceImpl;



	
	
	@GetMapping("/home")
	public String home(Model model) {
		
		model.addAttribute("title","Home-page");
		return "home";
	}
	

	@GetMapping("/signin")
	public String login() {
		
		String login ="This is a home page "; 
		return "login";
	}
	
	@GetMapping("/register")
	public String  register() {
		
		String register ="This is a home page "; 
		return "register";
	}
	


	
	 @PostMapping(value = "/do_register")
	 public String registerUser(User user,HttpSession httpSession,HttpServletRequest request)
	 {
		try {
			 
			String url= request.getRequestURI().toString();
			System.out.println(url);
			
			url= url.replace(request.getServletPath(), "");
		User saved =this.userService.createUser(user,url);
		 httpSession.setAttribute("customMessage", new Message());
			
		} catch (Exception e) {
		        e.printStackTrace();
		}
		              
		return"redirect:/register" ;
		
	 }
		
		  @GetMapping("/verify") public String verifyAccount(@Param("code")String
		  code,Model m) {
		  
		  boolean f= userServiceImpl.verifyAccount(code); if(f) {
		  m.addAttribute("msg","succsesfully your account is verified"); }else {
		  m.addAttribute("msg","your  verifiction code isincorrect "); }
		  
		  return"verifyemail"; }
		
	 
	
	

}
