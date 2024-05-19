package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Chat.ChatMessage;

import java.util.List;

public interface ChatMessageServices {
    ChatMessage save(ChatMessage chatMessage);

    List<ChatMessage> findChatMessages(
            String senderId,
            String recipientId
    );

    List<ChatMessage> findConversation(String senderID, String recipientID);
}
