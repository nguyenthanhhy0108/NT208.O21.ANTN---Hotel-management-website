package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Chat.ChatUser;
import com.example.hotel_management.Repository.ChatUserRepository;
import com.example.hotel_management.Service.ChatUserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatUserServicesImpl implements ChatUserServices {

    private final ChatUserRepository chatUserRepository;

    @Transactional
    public void saveUser(ChatUser user) {
        user.setStatus(0);
        this.chatUserRepository.save(user);
    }

    public void disconnect(ChatUser user) {
        var storedUser = this.chatUserRepository.findByNickName(user.getNickName());
        if (storedUser != null) {
            storedUser.setStatus(0);
            this.chatUserRepository.save(storedUser);
        }
    }

    public List<ChatUser> findConnected() {
        return this.chatUserRepository.findAllByStatus(1);
    }

    @Override
    public ChatUser findByNickname(String nickname) {
        return this.chatUserRepository.findByNickName(nickname);
    }

}
