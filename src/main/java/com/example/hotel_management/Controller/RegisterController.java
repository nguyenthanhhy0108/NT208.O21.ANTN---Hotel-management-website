package com.example.hotel_management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {
    @GetMapping("/register")
    String RegisterPage(){
        return "sign_up";
    }
}
