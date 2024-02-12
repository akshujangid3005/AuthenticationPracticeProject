	package com.authtentication.configration;
	
	import java.util.Arrays;
	import java.util.Collection;
	
	import org.springframework.security.core.GrantedAuthority;
	import org.springframework.security.core.authority.SimpleGrantedAuthority;
	import org.springframework.security.core.userdetails.UserDetails;
	
	import com.authtentication.entity.User;
	
	public class CoustomeUser implements UserDetails {
		
		
		private User user;
		 public CoustomeUser(User user) {
		        this.user = user;
		    }
	
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		
			return Arrays.asList(authority);
		}
	
		@Override
		public String getPassword() {
			
			return user.getUserPassword();
		}
	
		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return user.getUserEmail();
		}
	
		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}
	
		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return true;
		}
	
		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}
	
		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return true;
		}
	
	}
