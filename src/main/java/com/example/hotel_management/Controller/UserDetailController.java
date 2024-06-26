package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.DataDTO.BookingDTO;
import com.example.hotel_management.Model.DataDTO.WaitingRequest;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.RequestOwner;
import com.example.hotel_management.Model.UserDetails;
import com.example.hotel_management.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
                                HotelDetailsServices hotelDetailsServices,
                                RequestOwnerServices requestOwnerServices) {
        this.userDetailsServices = userDetailsServices;
        this.bookingServices = bookingServices;
        this.hotelServices = hotelServices;
        this.hotelDetailsServices = hotelDetailsServices;
    }

    public List<UserDetails> getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userDetailsServices.findByUsername(username);
    }


    @GetMapping("/profile")
    public String profile(Model model) {
        List<UserDetails> result = getUserDetails();
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

    @GetMapping("/profile/edit")
    public String editProfile(Model model) {
        List<UserDetails> result = getUserDetails();
        UserDetails userDetails = null;
        if (result.isEmpty()) {
            return "redirect:/login";
        }
        else {
            userDetails = result.get(0);
        }
        model.addAttribute("userDetails", userDetails);
        return "edit_profile";
    }

    @PostMapping("/profile/save")
    public String saveProfile(@ModelAttribute("userDetails") UserDetails userDetails,
                              Model model) {
        userDetailsServices.save(userDetails);
        return "redirect:/profile";
    }

    @ResponseBody
    @GetMapping("/loading-user-page")
        public WaitingRequest loadingUserPage(Authentication authentication) {
        WaitingRequest waitingRequest = new WaitingRequest();

        waitingRequest.setCustomerName(this.userDetailsServices
                .findByUsername(authentication.getName())
                .get(0).getName());

        waitingRequest.setSentBookings(this.bookingServices
                .convertToDTO(
                        this.bookingServices
                                .findByCustomer(authentication.getName())));

        List<String> sentHotelNames = new ArrayList<>();
        for (BookingDTO booking : waitingRequest.getSentBookings()) {
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

        waitingRequest.setReceivedBookings(
                this.bookingServices
                        .convertToDTO(
                                this.bookingServices
                                        .findByHotelId(hotelIDS)));

        List<String> receivedHotelNames = new ArrayList<>();
        List<String> receivedCustomerNames = new ArrayList<>();
        for (BookingDTO booking : waitingRequest.getReceivedBookings()) {
            receivedHotelNames.add(
                    this.hotelDetailsServices
                            .findByHotelID(
                                    booking.getHotelId())
                            .get(0)
                            .getName());

            receivedCustomerNames.add(
                    this.userDetailsServices
                            .findByUsername(booking.getCustomer())
                            .get(0)
                            .getName()
            );
        }
        waitingRequest.setReceivedHotelNames(receivedHotelNames);
        waitingRequest.setReceivedCustomerNames(receivedCustomerNames);

        return waitingRequest;
    }
}
