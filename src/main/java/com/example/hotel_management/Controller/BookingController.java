package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.*;
import com.example.hotel_management.Service.BookingServices;
import com.example.hotel_management.Service.HotelDetailsServices;
import com.example.hotel_management.Service.RoomServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static java.util.TimeZone.*;

@Controller
public class BookingController {

    private final HotelDetailsServices hotelDetailsServices;
    private final RoomServices roomServices;
    private final BookingServices bookingServices;
    /**
     * Dependency Injection
     * @param hotelDetailsServices: HotelDetailsServices object
     */
    @Autowired
    public BookingController(HotelDetailsServices hotelDetailsServices, RoomServices roomServices, BookingServices bookingServices) {
        this.hotelDetailsServices = hotelDetailsServices;
        this.roomServices = roomServices;
        this.bookingServices = bookingServices;
    }


    /**
     * Redirect to Booking page
     * @return
     * Get book_now.html
     */
    @GetMapping("/booking")
    public String BookingPage(@RequestParam("id") String roomID, Model model){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        Room room = roomServices.findRoomByID(roomID);

        if (room == null){
            model.addAttribute("cannotFindRoom", true);
            return "homepage";
        }
        else{
            HotelDetails hotelDetails = hotelDetailsServices.findById(room.getHotelID());
            model.addAttribute("roomID", roomID);
            model.addAttribute("numPeople", room.getNumPeople());
            model.addAttribute("hotelID", hotelDetails.getHotelID());
            String hotelName = hotelDetails.getName();
            model.addAttribute("hotelName", hotelName);
            return "book_now";
        }
    }

    @PostMapping("/booking")
    public  String requestBooking(HttpServletRequest request,
                               Model model) throws ParseException {
        HttpSession session = request.getSession();
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        Booking theBooking = new Booking();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(getTimeZone("Asian/Hanoi"));
        Date checkinDate = dateFormat.parse(request.getParameter("checkinDate"));
        Date checkoutDate = dateFormat.parse(request.getParameter("checkoutDate"));

        String userName = user.getName();

        String roomID = request.getParameter("roomID");
        String hotelID = request.getParameter("hotelID");

        theBooking.setCustomer(userName);
        theBooking.setCheckInDate(checkinDate);
        theBooking.setCheckOutDate(checkoutDate);
        theBooking.setRoomId(roomID);
        theBooking.setHotelId(hotelID);

        if (bookingServices.save(theBooking) != null){
            return "redirect:/profile";
        }
        else{
            HotelDetails hotelDetails = hotelDetailsServices.findById(hotelID);
            model.addAttribute("invalidBooking", true);
            model.addAttribute("roomID", roomID);
            model.addAttribute("hotelID", hotelID);

            String hotelName = hotelDetails.getName();
            model.addAttribute("hotelName", hotelName);
            return "book_now";
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBooking(@RequestParam("id") String bookingID){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        Booking deleteBooking = bookingServices.findById(bookingID);
        Date currentTime = new Date();

        if (deleteBooking != null){
            if (deleteBooking.getCustomer().equals(user.getName())){
                if (deleteBooking.getCheckInDate().getTime() > currentTime.getTime()){
                    deleteBooking = bookingServices.deleteByID(bookingID);
                    return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Successfully deleted booking!");
                }
                else{
                    return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.TEXT_PLAIN).body("It is too late to delete!");
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.TEXT_PLAIN).body("You are not the owner!");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body("Can not found the request");
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptBooking(@RequestParam("id") String bookingID, Model model){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        Booking acceptedBooking = bookingServices.acceptBookingById(bookingID, user.getName());

        if (acceptedBooking != null){
            return ResponseEntity.ok().body("Successfully accept booking!");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not accept the booking");
        }
    }

    @PostMapping("/refuse")
    public ResponseEntity<String> refuseBooking(@RequestParam("id") String bookingID, Model model){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        Booking refusedBooking = bookingServices.refuseBookingById(bookingID, user.getName());

        if (refusedBooking != null){
            return ResponseEntity.ok().body("Successfully refused booking!");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not refuse the booking");
        }
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completeBooking(@RequestParam("id") String bookingID, Model model){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        Booking completeBooking = bookingServices.completeBookingById(bookingID, user.getName());

        if (completeBooking != null){
            return ResponseEntity.ok().body("Successfully refused booking!");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not complete the booking");
        }
    }
}
