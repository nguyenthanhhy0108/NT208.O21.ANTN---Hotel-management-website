package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "REQUEST_OWNER")
public class RequestOwner {
    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer requestId;
    @Column(name = "username")
    private String username;
    @Column(name = "request_message")
    private String message;
    @Column(name = "is_accepted")
    private int isAccepted;
}
