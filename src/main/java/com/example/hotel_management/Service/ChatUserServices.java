package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Chat.ChatUser;

import java.util.List;

public interface ChatUserServices {
    void saveUser(ChatUser user);

    void disconnect(ChatUser user);

    List<ChatUser> findConnected();

    ChatUser findByNickname(String nickname);
}
