package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Service.HotelDetailsServices;
import com.example.hotel_management.Service.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HotelDetailController {

    private final HotelDetailsServices hotelDetailsServices;
    private final UserServices userServices;

    @Autowired
    HotelDetailController(HotelDetailsServices hotelDetailsServices,
                          UserServices userServices) {
        this.hotelDetailsServices = hotelDetailsServices;
        this.userServices = userServices;
    }

    @GetMapping("/hotel-detail")
    public String showHotelDetail(@RequestParam("hotel_id") String id,
                                  Model model,
                                  HttpServletRequest request) {
        HotelDetails hotelDetails = hotelDetailsServices.findById(id);
        model.addAttribute("hotel_detail", hotelDetails);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            return "hotel-details";
        }
        else{
            //Set attribute name in session
            HttpSession session = request.getSession();
            session.setAttribute("name", userServices.findByUsername(authentication.getName())
                    .get(0)
                    .getUserDetails()
                    .getName());
        }

        return "/hotel-details";
    }
}
