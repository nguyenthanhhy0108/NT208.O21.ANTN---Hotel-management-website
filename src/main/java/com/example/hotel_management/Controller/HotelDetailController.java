package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Hotel_detail;
import com.example.hotel_management.Service.HotelDetailServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HotelDetailController {
    private HotelDetailServices hotelDetailServices;
    @GetMapping("hotelDetail")
    public String showHotelDetail(@RequestParam("id") String id, Model model) {
        Hotel_detail hotel_detail = hotelDetailServices.findById(id);
        model.addAttribute("hotel_detail", hotel_detail);
        return "hotel-details";
    }
}
