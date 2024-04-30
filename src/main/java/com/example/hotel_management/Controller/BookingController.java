package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Model.UserDetails;
import com.example.hotel_management.Service.BookingServices;
import com.example.hotel_management.Service.HotelDetailsServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public String BookingPage(@RequestParam("id") String hotelID, Model model){
        HotelDetails hotelDetails = hotelDetailsServices.findById(hotelID);
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("hotelID", hotelDetails.getHotelID());
        return "book_now";
    }

    @PostMapping("/booking")
    public  String requestBooking(HttpServletRequest request,
                               Model model) throws ParseException {
        HttpSession session = request.getSession();
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        Booking theBooking = new Booking();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date checkinDate = dateFormat.parse(request.getParameter("checkinDate"));
        Date checkoutDate = dateFormat.parse(request.getParameter("checkoutDate"));

        String userName = user.getName();
        String hotelID = request.getParameter("hotelID");

        String numPeopleString = request.getParameter("numPeople");
        int numPeople = Integer.parseInt(numPeopleString);

        theBooking.setCustomer(userName);
        theBooking.setCheckInDate(checkinDate);
        theBooking.setCheckOutDate(checkoutDate);
        theBooking.setHotelId(hotelID);

        Booking assignedRoomBooking = bookingServices.assignRoomForBooking(theBooking, numPeople);

        if (assignedRoomBooking != null){
            this.bookingServices.save(theBooking);
            session.setAttribute("notifyBookingSuccessfully", true);
            return "homepage";
        }
        else{
            model.addAttribute("invalidBooking", true);
            model.addAttribute("hotelID", hotelID);
            return "book_now";
        }
    }

    @PutMapping("/deleteBooking")
    public String deleteBooking(HttpServletRequest request,
                                Model model){

    }
}
