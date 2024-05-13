package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Chat.ChatNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatNotificationRepository extends JpaRepository<ChatNotification, Integer> {
    ChatNotification findBySenderIdAndRecipientId(String senderId, String recipientId);
}
