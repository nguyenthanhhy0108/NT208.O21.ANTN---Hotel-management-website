package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Chat.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomServices {
    Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    );

    List<ChatRoom> findBySenderId(String senderId);
}
