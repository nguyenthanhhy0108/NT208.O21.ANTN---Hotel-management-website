package com.example.hotel_management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {
    @GetMapping("booking")
    public String BookingPage(){
        return "book_now";
    }
}
