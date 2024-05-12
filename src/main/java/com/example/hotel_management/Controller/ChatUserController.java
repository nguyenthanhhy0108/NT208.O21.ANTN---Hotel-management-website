package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Chat.ChatRoom;
import com.example.hotel_management.Model.Chat.ChatUser;
import com.example.hotel_management.Service.ChatRoomServices;
import com.example.hotel_management.Service.ChatUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatUserController {

    private final ChatUserServices chatUserServices;
    private final ChatRoomServices chatRoomServices;

    @Autowired
    public ChatUserController(ChatUserServices chatUserServices,
                              ChatRoomServices chatRoomServices) {
        this.chatUserServices = chatUserServices;
        this.chatRoomServices = chatRoomServices;
    }

    @MessageMapping("/app/user.addUser")
    @SendTo("/topic/public")
    public ChatUser addUser(@Payload ChatUser user) {
        System.out.println("abc");
        System.out.println(user);
        this.chatUserServices.saveUser(user);
        return user;
    }

    @MessageMapping("/app/user.disconnectUser")
    @SendTo("/topic/public")
    public ChatUser disconnect(@Payload ChatUser user) {
        this.chatUserServices.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<ChatUser>> findConnectedUsers() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<ChatRoom> chatRooms = this.chatRoomServices.findBySenderId(authentication.getName());

        List<ChatUser> result = new ArrayList<ChatUser>();
        for (ChatRoom chatRoom : chatRooms) {
            String recipientId = chatRoom.getRecipientId();
//            System.out.println(recipientId);
            result.add(this.chatUserServices.findByNickname(recipientId));
        }

        return ResponseEntity.ok(result);
    }
}
