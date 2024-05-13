package com.example.hotel_management.Model.Chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "CHAT_USER")
public class ChatUser {
    @Id
    @Column(name = "NickName")
    private String nickName;

    @Column(name = "UserName")
    private String fullName;

    @Column(name = "Status")
    private int status;
}
