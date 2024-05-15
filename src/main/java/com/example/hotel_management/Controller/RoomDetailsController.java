package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Booking;

import com.example.hotel_management.Model.DataDTO.RoomImageDTO;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Model.RoomImageRecord;

import com.example.hotel_management.Service.BookingServices;

import com.example.hotel_management.Service.HotelServices;
import com.example.hotel_management.Service.RoomImageRecordServices;
import com.example.hotel_management.Service.RoomServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Paths;

import java.util.*;

@Controller
public class RoomDetailsController {

    private final RoomServices roomServices;
    private final HotelServices hotelServices;
    private final RoomImageRecordServices roomImageRecordServices;

    private final BookingServices bookingServices;

    @Autowired
    public RoomDetailsController(RoomServices roomServices,
                                 HotelServices hotelServices,
                                 RoomImageRecordServices roomImageRecordServices,
                                 BookingServices bookingServices) {
        this.roomImageRecordServices = roomImageRecordServices;
        this.roomServices = roomServices;
        this.hotelServices = hotelServices;
        this.bookingServices = bookingServices;
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
        if (hotel.isEmpty() || hotel.get(0).getIsActive() == 0) {
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
        room.setRoomID(hotelId + '.' + room.getRoomID());
        room.setBookedGuests(0);
        model.addAttribute("room", room);
        return "add_room_form";
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
        return "add_room_form";
    }


    @PostMapping("/save_room")
    public String save_room(@Valid @ModelAttribute("room") Room room,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "add_room_form";
        }
        else {
            roomServices.saveRoom(room);
            return "redirect:/room-details?room_id=" + room.getRoomID();
        }
    }


    @GetMapping("/delete-room")
    public String delete_room(@RequestParam("room_id") String room_id) {
        Room room = roomServices.findByRoomID(room_id);
        if (room == null) {
            return "redirect:/first-page";
        }

        List<Hotel> hotel = hotelServices.findByHotelID(room.getHotelID());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!hotel.get(0).getOwnerUsername().equals(authentication.getName())){
            return "redirect:/first-page";
        }

        List<Booking> bookings = bookingServices.findByRoomId(room_id);
        if (bookings.isEmpty()){
            bookings = new ArrayList<Booking>();
        }


        boolean isFinish = bookings.stream().allMatch(t -> (t.getIsAccepted() == 2 || t.getIsAccepted() == 3));

        if (isFinish) {
            for (Booking booking : bookings) {
                bookingServices.deleteByID(String.valueOf(booking.getBookingId()));
            }
            roomServices.deleteRoomById(room_id);
            return "redirect:/hotel-detail?hotel_id=" + hotel.get(0).getHotelID();
        }

        else {
            return "redirect:/room-details?room_id=" + room.getRoomID();
        }

    }

    @GetMapping("/room-image")
    public String postRoomImageForm(@RequestParam("id") String roomID, Model model){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        if(!user.getName().equals(roomServices.findUserNameByRoomID(roomID))){
            return "redirect:/room-details?room_id="+roomID;
        }

        model.addAttribute("roomID", roomID);
        return "add_image_form";
    }

    @PostMapping("/room-image")
    public String postRoomImages(@RequestParam("roomID") String roomID, @RequestPart("files") MultipartFile[] files){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        if(!user.getName().equals(roomServices.findUserNameByRoomID(roomID))){
            return "redirect:/room-details?room_id="+roomID;
        }

        for (MultipartFile roomImage : files){
            roomImageRecordServices.uploadRoomImageUpdateDB(roomImage, roomID);
        }

        return "redirect:/room-details?room_id="+roomID;
    }

    @ResponseBody
    @GetMapping("/load-room-image")
    public RoomImageDTO getRoomImage(Authentication authentication, @RequestParam("id") String roomID){
        String userName = authentication.getName();

        RoomImageDTO roomImageDTO = roomImageRecordServices.getDTOByRoomID(roomID);
        roomImageDTO.setRoomId(roomID);

        if(userName.equals(this.roomServices.findUserNameByRoomID(roomID))){
            roomImageDTO.setIsOwner(true);
        }

        return roomImageDTO;
    }

    @DeleteMapping("/delete-room-image")
    public ResponseEntity<String> deleteBooking(@RequestParam("url") String publicURL){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        RoomImageRecord deleteRecord  = roomImageRecordServices.findByURL(publicURL);
        String roomID = deleteRecord.getRoomID();

        if(!user.getName().equals(roomServices.findUserNameByRoomID(roomID))){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.TEXT_PLAIN).body("You are not the owner of this room");
        }
        if(deleteRecord == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body("Can not found");
        }
        roomImageRecordServices.deleteRoomImageUpdateDB(publicURL);
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Successfully deleted the image!");
    }

    @GetMapping("/star-rating")
    public String starRating (){
        return "star_rating";
    }
}
