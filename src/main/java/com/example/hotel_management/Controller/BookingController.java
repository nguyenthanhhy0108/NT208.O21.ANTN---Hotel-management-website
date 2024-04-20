package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Service.HotelDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {

    private final HotelDetailsServices hotelDetailsServices;

    /**
     * Dependency Injection
     * @param hotelDetailsServices: HotelDetailsServices object
     */
    @Autowired
    public BookingController(HotelDetailsServices hotelDetailsServices) {
        this.hotelDetailsServices = hotelDetailsServices;
    }


    /**
     * Redirect to Booking page
     * @return
     * Get book_now.html
     */
    @GetMapping("/booking")
    public String BookingPage(@RequestParam("hotel_id") String hotel_id, Model model){
        HotelDetails hotelDetails = hotelDetailsServices.findById(hotel_id);
        model.addAttribute("hotel_id", hotelDetails);
        return "book_now";
    }
}
