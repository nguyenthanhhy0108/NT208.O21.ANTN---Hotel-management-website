package com.example.hotel_management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/first-page")
    String FirstPage(){
        return "first_page";
    }
    @GetMapping("/home")
    public String HomePage(){
        return "home";
    }
}
