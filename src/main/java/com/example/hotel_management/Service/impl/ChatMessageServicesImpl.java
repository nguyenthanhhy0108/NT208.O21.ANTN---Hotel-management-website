package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Chat.ChatMessage;
import com.example.hotel_management.Repository.ChatMessageRepository;
import com.example.hotel_management.Service.ChatMessageServices;
import com.example.hotel_management.Service.ChatRoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageServicesImpl implements ChatMessageServices {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomServices chatRoomServices;

    @Autowired
    public ChatMessageServicesImpl(ChatMessageRepository chatMessageRepository, ChatRoomServices chatRoomServices) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomServices = chatRoomServices;
    }

    @Transactional
    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomServices.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true
        ).orElse(null);
        chatMessage.setChatId(chatId);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(
            String senderId,
            String recipientId
    ) {
        var chatId = chatRoomServices.getChatRoomId(senderId, recipientId, false);
        return chatId.map(this.chatMessageRepository::findByChatId).orElse(new ArrayList<>());
    }

    @Override
    public List<ChatMessage> findConversation(String senderID, String recipientID) {
        return this.chatMessageRepository.findConversation(senderID, recipientID);
    }
}
