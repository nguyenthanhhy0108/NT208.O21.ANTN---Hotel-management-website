package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.DataDTO.WaitingRequest;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.UserDetails;
import com.example.hotel_management.Service.BookingServices;
import com.example.hotel_management.Service.HotelDetailsServices;
import com.example.hotel_management.Service.HotelServices;
import com.example.hotel_management.Service.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class UserDetailController {
    private final UserDetailsServices userDetailsServices;
    private final BookingServices bookingServices;
    private final HotelServices hotelServices;
    private final HotelDetailsServices hotelDetailsServices;

    @Autowired
    public UserDetailController(UserDetailsServices userDetailsServices,
                                BookingServices bookingServices,
                                HotelServices hotelServices,
                                HotelDetailsServices hotelDetailsServices) {
        this.userDetailsServices = userDetailsServices;
        this.bookingServices = bookingServices;
        this.hotelServices = hotelServices;
        this.hotelDetailsServices = hotelDetailsServices;
    }

    public List<UserDetails> getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userDetailsServices.findByUsername(username);
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        List<UserDetails> result = getUsername();
        UserDetails userDetails = null;
        if (result.isEmpty()) {
            return "redirect:/login";
        }
        else {
            userDetails = result.get(0);
        }
        model.addAttribute("userDetails", userDetails);
        return "user_profile";
    }

    @GetMapping("/request-owner")
    public String requestOwner(Model model) {
        List<UserDetails> result = getUsername();
        UserDetails userDetails = null;
        if (result.isEmpty()) {
            return "redirect:/login";
        }
        else {
            userDetails = result.get(0);
        }
        model.addAttribute("userDetails", userDetails);
        return "request_owner_form";
    }

    @ResponseBody
    @GetMapping("/loading-user-page")
        public WaitingRequest loadingUserPage(Authentication authentication) {
        WaitingRequest waitingRequest = new WaitingRequest();

        waitingRequest.setSentBookings(this.bookingServices
                .findByCustomer(authentication.getName()));

        List<String> sentHotelNames = new ArrayList<>();
        for (Booking booking : waitingRequest.getSentBookings()) {
            sentHotelNames.add(
                    this.hotelDetailsServices
                            .findByHotelID(
                                    booking.getHotelId())
                            .get(0)
                            .getName());
        }

        waitingRequest.setSentHotelNames(sentHotelNames);

        List<String> roles = new ArrayList<>();

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        if(roles.contains("ROLE_OWNER")){
            waitingRequest.setRoleOwner(true);
        }

        List<Hotel> hotels = this.hotelServices.findByOwnerUsername(authentication.getName());

        List<String> hotelIDS = new ArrayList<>();
        for (Hotel hotel : hotels) {
            hotelIDS.add(hotel.getHotelID());
        }

        waitingRequest.setReceivedBookings(this.bookingServices
                .findByHotelId(hotelIDS));

        List<String> receivedHotelNames = new ArrayList<>();
        for (Booking booking : waitingRequest.getReceivedBookings()) {
            receivedHotelNames.add(
                    this.hotelDetailsServices
                            .findByHotelID(
                                    booking.getHotelId())
                            .get(0)
                            .getName());
        }
        waitingRequest.setReceivedHotelNames(receivedHotelNames);

        return waitingRequest;
    }
}
