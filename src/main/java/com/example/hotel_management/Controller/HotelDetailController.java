package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Chat.ChatRoom;
import com.example.hotel_management.Model.Chat.ChatUser;
import com.example.hotel_management.Model.DataDTO.RoomDTO;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
public class HotelDetailController {

    private final HotelDetailsServices hotelDetailsServices;
    private final UserServices userServices;
    private final RoomServices roomServices;
    private final HotelServices hotelServices;
    private final HotelImageRecordServices hotelImageRecordServices;
    private final UserDetailsServices userDetailsServices;
    private final ChatRoomServices chatRoomServices;
    private final ChatUserServices chatUserServices;


    @Autowired
    HotelDetailController(HotelDetailsServices hotelDetailsServices,
                          UserServices userServices,
                          RoomServices roomServices,
                          HotelServices hotelServices,
                          HotelImageRecordServices hotelImageRecordServices,
                          ChatRoomServices chatRoomServices,
                          ChatUserServices chatUserServices,
                          UserDetailsServices userDetailsServices) {
        this.hotelDetailsServices = hotelDetailsServices;
        this.userServices = userServices;
        this.roomServices = roomServices;
        this.hotelServices = hotelServices;
        this.hotelImageRecordServices = hotelImageRecordServices;
        this.chatRoomServices = chatRoomServices;
        this.chatUserServices = chatUserServices;
        this.userDetailsServices = userDetailsServices;
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
    public String updateHotel(@RequestParam("hotel_id") String hotel_id,
                              Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("anonymousUser")){
            return "hotel-details";
        }
        List<Hotel> hotel = hotelServices.findByHotelID(hotel_id);
        if (hotel.isEmpty() || !Objects.equals(hotel.get(0).getOwnerUsername(), authentication.getName())) {
            return "redirect:/hotel-detail/your-hotel";
        }
        HotelDetails hotelDetails = hotelDetailsServices.findById(hotel_id);

        model.addAttribute("hotel", hotel.get(0));
        model.addAttribute("hotel_detail", hotelDetails);

        return "create_hotel_form";
    }


    @PostMapping("/add-hotel")
    public String addHotel(@Valid @ModelAttribute("hotel") Hotel hotel,
                           @Valid @ModelAttribute("hotel_detail") HotelDetails hotelDetails,
                           BindingResult bindingResult){


        if (bindingResult.hasErrors()) {
            return "create_hotel_form";
        }

        hotelDetails.setHotelID(hotel.getHotelID());
        hotelServices.saveHotel(hotel);
        hotelDetailsServices.save(hotelDetails);

        return "redirect:/hotel-detail?hotel_id=" + hotel.getHotelID();
    }

    @GetMapping("delete-hotel")
    public String deleteHotel(@RequestParam("hotel_id") String hotel_id, Model model) {
        List<Hotel> hotel = hotelServices.findByHotelID(hotel_id);
        if (hotel.isEmpty()){
            return "redirect:/hotel-detail/your-hotels";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!hotel.get(0).getOwnerUsername().equals(authentication.getName()) || hotel.get(0).getIsActive() != 0) {
            return "redirect:/hotel-detail";
        }

        if (hotel.get(0).getIsActive() == 0) {
            HotelDetails hotelDetails = hotelDetailsServices.findById(hotel_id);
            hotelDetailsServices.delete(hotelDetails.getHotelID());
            hotelServices.deleteByHotelId(hotel_id);
        }
        return "redirect:/hotel-detail/your-hotels";
    }

    @GetMapping("/hotel-detail/your-hotels")
    public String yourHotels(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals("anonymousUser")){
            return "redirect:/login";
        }
        if (!request.isUserInRole("ROLE_OWNER")){
            return "redirect:/hotel-detail";
        }

        String[] status = {"Rejected", "Waiting" ,"Accepted"};
        List<Hotel> hotel = hotelServices.findByOwnerUsername(authentication.getName());
        List<Map<String, String>> hotels = new ArrayList<>(List.of());
        for (Hotel _hotel : hotel) {
            Map<String, String> newHotel = new HashMap<>();
            HotelDetails hotelDetails = hotelDetailsServices.findById(_hotel.getHotelID());
            newHotel.put("hotel_id", _hotel.getHotelID());
            newHotel.put("hotel_name", hotelDetails.getName());
            newHotel.put("status", status[_hotel.getIsActive()+1]);
            hotels.add(newHotel);
        }

        model.addAttribute("hotels", hotels);
        return "owner_hotels";
    }

    @GetMapping("/accept-hotel")
    public String acceptHotel(@RequestParam("hotel_id") String hotel_id,
                              HttpServletRequest request,
                              Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals("anonymousUser")){
            return "redirect:/login";
        }

        if (!request.isUserInRole("ROLE_ADMIN")){
            return "redirect:/hotel-detail";
        }

        List<Hotel> hotel = hotelServices.findByHotelID(hotel_id);
        if (hotel.isEmpty()){
            return "redirect:/hotel-requests";
        }
        hotel.get(0).setIsActive(1);
        hotelServices.saveHotel(hotel.get(0));
        return "redirect:/hotel-requests";
    }

    @GetMapping("/reject-hotel")
    public String rejectHotel(@RequestParam("hotel_id") String hotel_id,
                              HttpServletRequest request,
                              Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals("anonymousUser")){
            return "redirect:/login";
        }

        if (!request.isUserInRole("ROLE_ADMIN")){
            return "redirect:/hotel-detail";
        }

        List<Hotel> hotel = hotelServices.findByHotelID(hotel_id);
        if (hotel.isEmpty()){
            return "redirect:/hotel-requests";
        }
        hotel.get(0).setIsActive(-1);
        hotelServices.saveHotel(hotel.get(0));
        return "redirect:/hotel-requests";
    }

    @GetMapping("hotel-requests")
    public String hotelRequests(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals("anonymousUser")){
            return "redirect:/login";
        }
        if (!request.isUserInRole("ROLE_ADMIN")){
            return "redirect:/hotel-detail";
        }

        List<Hotel> hotel = hotelServices.findByIsActive(0);

        List<Map<String, String>> hotels = new ArrayList<>(List.of());
        for (Hotel _hotel : hotel){
            Map<String, String> newHotel = new HashMap<>();
            HotelDetails hotelDetails = hotelDetailsServices.findById(_hotel.getHotelID());
            newHotel.put("hotel_id", _hotel.getHotelID());
            newHotel.put("hotel_name", hotelDetails.getName());
            newHotel.put("owner_name", _hotel.getOwnerUsername());
            hotels.add(newHotel);
        }
        model.addAttribute("hotels", hotels);
        return "hotel_requests";
    }


    @PostMapping("create-chat-room")
    public ResponseEntity<Map<String, Object>> createChatRoom(@RequestParam("hotelID") String hotelID) {
        Map<String, Object> response = new HashMap<>();

        String hotelOwner = this.hotelServices.findByHotelID(hotelID).get(0).getOwnerUsername();

        ChatUser chatUser = this.chatUserServices.findByNickname(hotelOwner);
        if (chatUser == null){
            chatUser = new ChatUser();
            chatUser.setNickName(hotelOwner);
            chatUser.setFullName(this.userDetailsServices.findByUsername(hotelOwner).get(0).getName());
            chatUser.setStatus(0);
            this.chatUserServices.saveUser(chatUser);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<ChatRoom> listChatRoom1 = this.chatRoomServices.findBySenderId(authentication.getName());
        if (listChatRoom1.isEmpty()){
            ChatRoom chatRoom1 = new ChatRoom();
            chatRoom1.setChatId(String.format("%s_%s", authentication.getName(), hotelOwner));
            chatRoom1.setSenderId(authentication.getName());
            chatRoom1.setRecipientId(hotelOwner);
            chatRoomServices.save(chatRoom1);
        }

        List<ChatRoom> listChatRoom2 = this.chatRoomServices.findBySenderId(hotelOwner);
        if (listChatRoom2.isEmpty()){
            ChatRoom chatRoom2 = new ChatRoom();
            chatRoom2.setChatId(String.format("%s_%s", hotelOwner, authentication.getName()));
            chatRoom2.setSenderId(hotelOwner);
            chatRoom2.setRecipientId(authentication.getName());
            chatRoomServices.save(chatRoom2);
        }

        response.put("status", true);
        return ResponseEntity.ok(response);
    }
}
