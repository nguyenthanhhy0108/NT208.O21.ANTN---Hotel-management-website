package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Hotel_detail;
import com.example.hotel_management.Repository.BookingRepository;
import com.example.hotel_management.Repository.HotelDetailRepository;
import com.example.hotel_management.Repository.UserRepository;
import com.example.hotel_management.Service.HotelDetailServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {

    private final UserRepository userRepository;
    private final HotelDetailServices hotelDetailServices;

    public BookingController(UserRepository userRepository, HotelDetailServices hotelDetailServices) {
        this.userRepository = userRepository;
        this.hotelDetailServices = hotelDetailServices;
    }


    /**
     * Redirect to Booking page
     * @return
     * Get book_now.html
     */
    @GetMapping("booking")
    public String BookingPage(@RequestParam("hotel_id") String hotel_id, Model model){
        Hotel_detail hotel_detail = hotelDetailServices.findById(hotel_id);
        model.addAttribute("hotel_id", hotel_detail);
        return "book_now";
    }
}
