package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Service.HotelDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HotelDetailController {

    private HotelDetailsServices hotelDetailsServices;

    @Autowired
    HotelDetailController(HotelDetailsServices hotelDetailsServices) {
        this.hotelDetailsServices = hotelDetailsServices;
    }

    @GetMapping("/hotel-detail")
    public String showHotelDetail(@RequestParam("id") String id, Model model) {
        HotelDetails hotelDetails = hotelDetailsServices.findById(id);
        model.addAttribute("hotel_detail", hotelDetails);
        return "/hotel-details";
    }
}
