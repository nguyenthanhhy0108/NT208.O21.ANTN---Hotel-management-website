package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Service.BookingServices;
import com.example.hotel_management.Service.HotelDetailsServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

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
    public String BookingPage(@RequestParam("hotel_id") String hotelID, Model model){
        HotelDetails hotelDetails = hotelDetailsServices.findById(hotelID);
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("hotelDetail", hotelDetails);
        // model.addAttribute("userID", user.getUsername);
        return "book_now";
    }

    @PostMapping("/booking")
    public  String saveBooking(HttpServletRequest request,
                               HttpServletResponse response,
                               Model model){
        Booking theBooking = new Booking();

        String Customer = request.getParameter("");
        String checkinDate = request.getParameter("");
        String checkoutDate = request.getParameter("");
        String hotelID = request.getParameter("")


        theBooking.setCustomer();
        theBooking.setCheckInDate();
        theBooking.setCheckOutDate();
        theBooking.setTotalPrice();
        theBooking.setHotelId();

        this.bookingServices.save(theBooking);
        return "first-page";
    }


}
