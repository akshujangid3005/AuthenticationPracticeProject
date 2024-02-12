	package com.authtentication.controller;
	import java.util.Random;
	
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestParam;

import com.authtentication.entity.User;
import com.authtentication.repository.UserRepository;
import com.authtentication.service.impl.EmailServiceImpl;
	
	import jakarta.servlet.http.HttpSession;
	
	@Controller
	public class ForgotPasswordController {
		
		@Autowired
		private BCryptPasswordEncoder bCryptPasswordEncoder;
		
		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private EmailServiceImpl emailServiceImpl;
	    
	    @GetMapping("/forgotpassword")
	    public String openForgotPasswordForm() {
	        return "forgotpassword";
	    }
	    
	    @PostMapping("/send-otp")
	    public String sendOTP(@RequestParam("email") String email, HttpSession httpSession)
	    { System.out.println("Email " + email);

	    User user = this.userRepository.findByUserEmail(email); if (user == null) { httpSession.setAttribute("message", "User does not exist with this email. Please register first."); return "forgotpassword"; }

	    // generate 6 digit OTP
	    Random random = new Random();
	    int otp = random.nextInt(999999);
	    System.out.println("OTP " + otp);

	    String subject = "OTP from Authentication"; 
	    String message = "<h1> OTP = " + otp + " </h1>"; 
	    String to = email;

	    boolean flag = this.emailServiceImpl.sendEmail(subject, message, to); 
	    if (flag) {
	    	httpSession.setAttribute("myOtp", otp);
	    	httpSession.setAttribute("userEmail", email);
	    }

	    return "verify_otp"; // Return the name of the HTML page to show verify OTP form } else { httpSession.setAttribute("message", "Failed to send OTP. Please try again."); return "forgotpassword"; // Return to forgotpassword page if sending OTP fails } }

	    }
	    
	    @PostMapping("/forgotpassword/verify-otp")
	    public String verifyotp(@RequestParam("otp") int  otp,HttpSession httpSession) {
	    	int myOtp=(int) httpSession.getAttribute("myOtp");
	    	String email= (String) httpSession.getAttribute("userEmail");
	    	if(myOtp==otp) {
	    		
	    	User user=	this.userRepository.findByUserEmail(email);
	    	
	    	if (user==null) {
                   httpSession.setAttribute("message", "User doesnot exist with this email  ");
				
				return "forgotpassword";
				// send error message
			}else {
				return "forgotpasswordchangepage";
				
				// send forgot password change page
			}
	    		
	    		
	    		
	    		// return password change page 
	    	}else {
	    		httpSession.setAttribute("message"," you have entered Wrong Otp");
	    		return "verify_otp";
	    	}
	    	
	    	 }
	    @PostMapping("/changepassword")
	    public String changePassword(@RequestParam("newpassword") String newpassword,HttpSession httpSession) {
	    	String email= (String) httpSession.getAttribute("userEmail");
	    	User user=this.userRepository.findByUserEmail(email);
	    	user.setUserPassword(this.bCryptPasswordEncoder.encode(newpassword));
	    	this.userRepository.save(user);
	    	System.out.println("password updated");
	    	
	    	return "login";
	    	
	    	
	    	
	    	
	    	
	    }
	
	    }
	
	
