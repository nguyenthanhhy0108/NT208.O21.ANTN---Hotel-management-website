package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

//This class is match with HOTEL table in database
@Data
@Entity(name = "COMMENT")
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String commentID;

    @Column(name = "user_name")
    private String username;

    @Column(name = "comment")
    private String comment;

    @Column(name="room_id")
    private String roomID;

    @Column(name="star")
    private int star;

    public Comment(){

    }
}
