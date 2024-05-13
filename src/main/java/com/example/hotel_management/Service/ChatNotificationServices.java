package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Chat.ChatNotification;

import java.util.List;

public interface ChatNotificationServices {
    ChatNotification save(ChatNotification notification);
    ChatNotification findBySenderIdAndRecipientId(String senderId, String recipientId);
}
