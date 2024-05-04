package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Service.HotelServices;
import com.example.hotel_management.Service.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class RoomDetailsController {

    private final RoomServices roomServices;
    private final HotelServices hotelServices;

    @Autowired
    public RoomDetailsController(RoomServices roomServices,
                                 HotelServices hotelServices) {
        this.roomServices = roomServices;
        this.hotelServices = hotelServices;
    }

    /**
     * Redirect room details page
     * @param roomId: String
     * @return
     * Room detail page if true otherwise redirect to first page
     */
    @GetMapping("/room-details")
    public String getRoomDetailsPage(@RequestParam(value = "room_id", required = false) String roomId) {
        if(roomId == null || roomId.isEmpty()) {
            return "first_page";
        }
        return "room-details";
    }

    @ResponseBody
    @GetMapping("/get-room-details")
    public ResponseEntity<Map<String, Object>> getRoomDetails(@RequestParam(value = "room_id") String roomId) {
        Map<String, Object> response = new HashMap<>();

        response.put("room", this.roomServices.toDTO(
                this.roomServices.findByRoomID(roomId)));

        return ResponseEntity.ok(response);
    }

    @GetMapping("add-room")
    public String addRoom(@RequestParam("hotel_id") String hotelId, Model model) {
        List<Hotel> hotel = hotelServices.findByHotelID(hotelId);
        if (hotel.isEmpty()) {
            return "redirect:/first-page";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            return "redirect:/login";
        }
        String username = authentication.getName();
        if (!Objects.equals(hotel.get(0).getOwnerUsername(), username)) {
            return "redirect:/first-page";
        }

        Room room = new Room();
        room.setHotelID(hotelId);
        room.setBookedGuests(0);
        model.addAttribute("room", room);
        return "add_room";
    }

    @GetMapping("update-room")
    public String updateRoom(@RequestParam("room_id") String room_id, Model model) {
        Room room = roomServices.findByRoomID(room_id);
        if (room == null) {
            return "redirect:/first-page";
        }
        List<Hotel> hotel = hotelServices.findByHotelID(room.getHotelID());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            return "redirect:/login";
        }
        String username = authentication.getName();
        if (!Objects.equals(hotel.get(0).getOwnerUsername(), username)) {
            return "redirect:/first-page";
        }

        model.addAttribute("room", room);
        return "add_room";
    }


    @PostMapping("/save_room")
    public String save_room(@ModelAttribute("room") Room room) {
        roomServices.saveRoom(room);
        return "redirect:/room-details?room_id=" + room.getRoomID();
    }
}
