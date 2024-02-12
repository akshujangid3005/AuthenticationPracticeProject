
  package com.authtentication.configration;
  
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.context.annotation.Bean; import
  org.springframework.context.annotation.Configuration; import
  org.springframework.security.authentication.dao.DaoAuthenticationProvider;
  import
  org.springframework.security.config.annotation.web.builders.HttpSecurity;
  import org.springframework.security.config.annotation.web.configuration.
  EnableWebSecurity; import
  org.springframework.security.core.userdetails.UserDetailsService; import
  org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  import
  org.springframework.security.web.SecurityFilterChain;

  @Configuration
  
  @EnableWebSecurity
  public class SecurityConfig {

    @Autowired
    public CoustomAuthenticationHandler coustomAuthenticationHandler;
  
  @Bean
  public BCryptPasswordEncoder passwordEncoder()
  { 
	  return new BCryptPasswordEncoder(); 
	  }
  
  @Bean public UserDetailsService userDetailsService() { 
	  return new  CoustomeUserDetailsServices(); 
	  }
  
  @Bean public DaoAuthenticationProvider daoAuthenticationProvider() {
  DaoAuthenticationProvider daoAuthenticationProvider = new
  DaoAuthenticationProvider();
  daoAuthenticationProvider.setUserDetailsService(userDetailsService());
  daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); 
  return daoAuthenticationProvider;
  
  }
  @Bean public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws
  
  Exception{ httpSecurity .csrf(csrf -> csrf.disable())
 
	  .authorizeHttpRequests(requests -> requests
  
	  .requestMatchers("/auth/user/**").hasRole("USER")
                  
	  .requestMatchers("/auth/admin/**").hasRole("ADMIN")

  .requestMatchers("/register","/do_register","/home","/forgotpassword","/send-otp","/forgotpassword/verify-otp","/changepassword") .permitAll()
  )
  
  .formLogin(form -> form
		  
  .loginPage("/signin").loginProcessingUrl("/userLogin")
          .successHandler(coustomAuthenticationHandler)
          
  .defaultSuccessUrl("/auth/user/index",true)
          .permitAll());
return httpSecurity.build(); }
  
  }
 
