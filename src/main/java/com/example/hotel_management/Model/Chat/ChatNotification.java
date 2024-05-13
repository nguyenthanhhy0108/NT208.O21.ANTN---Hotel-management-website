package com.example.hotel_management.Model.Chat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "CHAT_NOTIFICATION")
public class ChatNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "SenderId")
    private String senderId;

    @Column(name = "RecipientId")
    private String recipientId;

    @Column(name = "is_Noticed")
    private int isNoticed;

    public ChatNotification(String senderId, String recipientId, int isNoticed) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.isNoticed = isNoticed;
    }
}
