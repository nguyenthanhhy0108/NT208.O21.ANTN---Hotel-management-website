package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Service.BookingServices;
import com.example.hotel_management.Service.HotelDetailsServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {

    private final HotelDetailsServices hotelDetailsServices;
    private final BookingServices bookingServices;
    /**
     * Dependency Injection
     * @param hotelDetailsServices: HotelDetailsServices object
     */
    @Autowired
    public BookingController(HotelDetailsServices hotelDetailsServices, BookingServices bookingServices) {
        this.hotelDetailsServices = hotelDetailsServices;
        this.bookingServices = bookingServices;
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

    @PostMapping("/booking")
    public  String saveBooking(HttpServletRequest request,
                               HttpServletResponse response,
                               Model model){
        this.bookingServices.save(theBooking);
        return "first-page";
    }


}
