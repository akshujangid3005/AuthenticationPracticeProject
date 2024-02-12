package com.authtentication.controller;

import com.authtentication.entity.User;
import com.authtentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;

@Controller
@RequestMapping("/auth")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void commonProfile(Principal principal, Model m) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByUserEmail(email);
            m.addAttribute("user", user);
        }
    }

    @GetMapping("/admin/profile")
    public String profile() {
        return "User/admin_profile";
    }
}

