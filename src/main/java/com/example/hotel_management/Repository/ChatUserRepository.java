package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Chat.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, String> {

    ChatUser findByNickName(String nickName);

    List<ChatUser> findAllByStatus(int status);
}
