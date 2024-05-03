package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.DataDTO.RoomDTO;
import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Service.HotelDetailsServices;
import com.example.hotel_management.Service.RoomServices;
import com.example.hotel_management.Service.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HotelDetailController {

    private final HotelDetailsServices hotelDetailsServices;
    private final UserServices userServices;
    private final RoomServices roomServices;

    @Autowired
    HotelDetailController(HotelDetailsServices hotelDetailsServices,
                          UserServices userServices,
                          RoomServices roomServices) {
        this.hotelDetailsServices = hotelDetailsServices;
        this.userServices = userServices;
        this.roomServices = roomServices;
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

    @GetMapping("/get-list-rooms")
    @ResponseBody
    public ResponseEntity<List<RoomDTO>> getAllAvailableRooms(@RequestParam("hotel_id") String hotelID,
                                                              HttpServletRequest request){

        HttpSession session = request.getSession();

        String checkInDate = (String) session.getAttribute("checkInDate");
        String checkOutDate = (String) session.getAttribute("checkOutDate");

//        System.out.println(hotelID);
//
//        System.out.println(checkInDate);
//        System.out.println(checkOutDate);

        List<Room> availableRooms = this.roomServices.findAvailableRoomForBooking(hotelID, checkInDate, checkOutDate);

        return ResponseEntity.ok(this.roomServices.toDTO(availableRooms));
    }
}
