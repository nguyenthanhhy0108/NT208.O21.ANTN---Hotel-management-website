package com.example.hotel_management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    //Return login page, which is sign_in.html
    @GetMapping("/login")
    public String loginPage(){
        return "sign_in";
    }
}
