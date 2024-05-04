package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.DataDTO.RoomDTO;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Service.HotelDetailsServices;
import com.example.hotel_management.Service.HotelServices;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class HotelDetailController {

    private final HotelDetailsServices hotelDetailsServices;
    private final UserServices userServices;
    private final RoomServices roomServices;
    private final HotelServices hotelServices;

    @Autowired
    HotelDetailController(HotelDetailsServices hotelDetailsServices,
                          UserServices userServices,
                          RoomServices roomServices,
                          HotelServices hotelServices) {
        this.hotelDetailsServices = hotelDetailsServices;
        this.userServices = userServices;
        this.roomServices = roomServices;
        this.hotelServices = hotelServices;
    }

    @GetMapping("/hotel-detail")
    public String showHotelDetail(@RequestParam(value = "hotel_id", required = false ) String id,
                                  Model model,
                                  HttpServletRequest request) {
        if(id == null || id.isEmpty()) {
            return "first_page";
        }

        List<Hotel> hotel = hotelServices.findByHotelID(id);
        if (hotel.isEmpty()) {
            return "redirect:/hotel-detail";
        }
        model.addAttribute("hotel", hotel.get(0));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Boolean isOwner = Objects.equals(hotel.get(0).getOwnerUsername(), auth.getName());
        model.addAttribute("isOwner", isOwner);

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

    @GetMapping("/hotel-register")
    public String createHotel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            return "hotel-details";
        }

        Hotel hotel = new Hotel(authentication.getName());
        HotelDetails hotelDetails = new HotelDetails();

        model.addAttribute("hotel", hotel);
        model.addAttribute("hotel_detail", hotelDetails);

        return "create_hotel_form";
    }

    @GetMapping("/update-hotel")
    public String updateHotel(@RequestParam("hotel_id") String hotel_id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            return "hotel-details";
        }

        List<Hotel> hotel = hotelServices.findByHotelID(hotel_id);
        if (hotel.isEmpty() || hotel.get(0).getOwnerUsername() != authentication.getName()) {
            return "redirect:/hotel-detail";
        }
        HotelDetails hotelDetails = hotelDetailsServices.findById(hotel_id);

        model.addAttribute("hotel", hotel);
        model.addAttribute("hotel_detail", hotelDetails);

        return "create_hotel_form";
    }


    @PostMapping("/add-hotel")
    public String addHotel(@ModelAttribute("hotel") Hotel hotel,
                           @ModelAttribute("hotel_detail") HotelDetails hotelDetails){

        hotelDetails.setHotelID(hotel.getHotelID());
        hotelServices.saveHotel(hotel);
        hotelDetailsServices.save(hotelDetails);

        return "redirect:/hotel-detail";
    }

    @GetMapping("delete-hotel")
    public String deleteHotel(@RequestParam("hotel_id") String hotel_id, Model model) {
        List<Hotel> hotel = hotelServices.findByHotelID(hotel_id);
        if (hotel.isEmpty()){
            return "redirect:/hotel-detail";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!hotel.get(0).getOwnerUsername().equals(authentication.getName()) || hotel.get(0).getIsActive() != 0) {
            return "redirect:/hotel-detail";
        }

        HotelDetails hotelDetails = hotelDetailsServices.findById(hotel_id);
        hotelDetailsServices.delete(hotelDetails.getHotelID());
        hotelServices.deleteByHotelId(hotel_id);
        return "redirect:/hotel-detail";
    }
}
