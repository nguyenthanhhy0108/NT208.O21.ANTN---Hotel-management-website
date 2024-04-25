package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.UserDetails;
import com.example.hotel_management.Service.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserDetailController {
    private UserDetailsServices userDetailsServices;

    @Autowired
    public UserDetailController(UserDetailsServices userDetailsServices) {
        this.userDetailsServices = userDetailsServices;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<UserDetails> result = userDetailsServices.findByUsername(username);
        UserDetails userDetails = null;
        if (result.isEmpty()) {
            return "redirect:/login";
        }
        else {
            userDetails = result.get(0);
        }
        model.addAttribute("userDetails", userDetails);
        return "user_profile";

    }
}
