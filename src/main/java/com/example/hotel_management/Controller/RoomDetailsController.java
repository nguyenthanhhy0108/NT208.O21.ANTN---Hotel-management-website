package com.example.hotel_management.Controller;

import com.example.hotel_management.Service.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RoomDetailsController {

    private final RoomServices roomServices;

    @Autowired
    public RoomDetailsController(RoomServices roomServices) {
        this.roomServices = roomServices;
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
}
