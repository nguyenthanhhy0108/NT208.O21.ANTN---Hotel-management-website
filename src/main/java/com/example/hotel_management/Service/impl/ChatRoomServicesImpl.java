package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Chat.ChatRoom;
import com.example.hotel_management.Repository.ChatRoomRepository;
import com.example.hotel_management.Service.ChatRoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomServicesImpl implements ChatRoomServices {

    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatRoomServicesImpl(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ) {
        return this.chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        return Optional.ofNullable(createChat(senderId, recipientId));
                    }
                    return Optional.empty();
                });
    }

    @Override
    public List<ChatRoom> findBySenderId(String senderId) {
        return this.chatRoomRepository.findBySenderId(senderId);
    }

    private String createChat(String senderId, String recipientId) {
        var chatId = String.format("%s_%s", senderId, recipientId);
        ChatRoom senderRecipient = new ChatRoom(chatId, senderId, recipientId);

        ChatRoom recipientSender = new ChatRoom(chatId, recipientId, senderId);

        this.chatRoomRepository.save(senderRecipient);
        this.chatRoomRepository.save(recipientSender);
        return chatId;
    }

}
