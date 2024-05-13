package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Chat.ChatMessage;
import com.example.hotel_management.Model.Chat.ChatNotification;
import com.example.hotel_management.Model.Chat.ChatUser;
import com.example.hotel_management.Service.ChatMessageServices;
import com.example.hotel_management.Service.ChatNotificationServices;
import com.example.hotel_management.Service.ChatUserServices;
import com.example.hotel_management.Service.UserDetailsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageServices chatMessageService;
    private final ChatNotificationServices chatNotificationService;
    private final UserDetailsServices userDetailsServices;
    private final ChatUserServices chatUserServices;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        ChatNotification chatNotification = this.chatNotificationService
                .findBySenderIdAndRecipientId(savedMsg.getSenderId(), savedMsg.getRecipientId());
        if (chatNotification == null) {
            chatNotification = this.chatNotificationService.save(new ChatNotification(
                    savedMsg.getSenderId(),
                    savedMsg.getRecipientId(),
                    1
            ));
        }
        else {
            chatNotification.setIsNoticed(1);
        }

        ChatNotification receivedChatNotification = this.chatNotificationService
                .findBySenderIdAndRecipientId(savedMsg.getRecipientId(), savedMsg.getSenderId());

        if (receivedChatNotification != null) {
            receivedChatNotification.setIsNoticed(0);
            this.chatNotificationService.save(receivedChatNotification);
        }

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/queue/messages",
                this.chatNotificationService.save(chatNotification)
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable String senderId,
                                                              @PathVariable String recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findConversation(senderId));
    }

    @GetMapping("/chat")
    public String getChatPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ChatUser chatUser = this.chatUserServices.findByNickname(authentication.getName());
        if(chatUser == null) {
            chatUser = new ChatUser();
            chatUser.setNickName(authentication.getName());
            chatUser.setFullName(this.userDetailsServices.findByUsername(authentication.getName()).get(0).getName());
            chatUser.setStatus(0);

            this.chatUserServices.saveUser(chatUser);
        }
        return "chat";
    }

    @GetMapping("/get-information")
    public ResponseEntity<Map<String, String>> getInformation() {
        Map<String, String> response = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        response.put("nickName", authentication.getName());
        response.put("fullName", this.userDetailsServices.findByUsername(authentication.getName()).get(0).getName());

        return ResponseEntity.ok(response);
    }
}
