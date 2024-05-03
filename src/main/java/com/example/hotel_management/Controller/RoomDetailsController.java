package com.example.hotel_management.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomDetailsController {

    @GetMapping("/room-details")
    public String getRoomDetailsPage() {
        return "room-details";
    }
}
