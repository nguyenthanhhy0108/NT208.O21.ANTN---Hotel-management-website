package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    List<ChatMessage> findByChatId(String chatId);

    @Query("SELECT C FROM CHAT_MESSAGE AS C WHERE C.senderId = :senderID OR C.recipientId = :senderID")
    List<ChatMessage> findConversation(String senderID);
}
