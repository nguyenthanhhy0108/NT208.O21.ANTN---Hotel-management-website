package com.example.hotel_management.Model.Chat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "CHAT_MESSAGE")
public class ChatMessage {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ChatId")
    private String chatId;

    @Column(name = "SenderId")
    private String senderId;

    @Column(name = "RecipientId")
    private String recipientId;

    @Column(name = "Content")
    private String content;

    @Column(name = "Timestamp")
    private Date timestamp;
}