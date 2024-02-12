	package com.authtentication.configration;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.core.userdetails.UsernameNotFoundException;
	
	import org.springframework.stereotype.Service;
	
	import com.authtentication.entity.User;
	import com.authtentication.repository.UserRepository;
	
	@Service
	public class CoustomeUserDetailsServices implements UserDetailsService{
		
		@Autowired
		private UserRepository userRepository;
	
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
			
			User user=userRepository.findByUserEmail(username);
			
			if (user==null) {
				throw new UsernameNotFoundException("user not found");
				
			}
			return new CoustomeUser(user);
		}
	
	}
