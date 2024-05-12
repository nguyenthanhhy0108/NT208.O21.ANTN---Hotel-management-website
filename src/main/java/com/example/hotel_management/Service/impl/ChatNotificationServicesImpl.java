package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Chat.ChatNotification;
import com.example.hotel_management.Repository.ChatNotificationRepository;
import com.example.hotel_management.Service.ChatNotificationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatNotificationServicesImpl implements ChatNotificationServices {

    private final ChatNotificationRepository chatNotificationRepository;

    @Autowired
    public ChatNotificationServicesImpl(ChatNotificationRepository chatNotificationRepository) {
        this.chatNotificationRepository = chatNotificationRepository;
    }

    @Transactional
    @Override
    public ChatNotification save(ChatNotification notification) {
        return this.chatNotificationRepository.save(notification);
    }

    @Override
    public ChatNotification findBySenderIdAndRecipientId(String senderId, String recipientId) {
        return this.chatNotificationRepository.findBySenderIdAndRecipientId(senderId, recipientId);
    }
}
