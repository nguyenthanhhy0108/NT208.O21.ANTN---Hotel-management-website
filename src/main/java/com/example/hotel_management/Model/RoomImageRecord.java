package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "ROOM_IMAGE")
public class RoomImageRecord {
    @Id
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recordID;

    @Column(name = "URL")
    private String URL;

    @Column(name = "room_id")
    private String roomID;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "room_id",
            insertable = false,
            updatable = false)
    private Room room;

    public RoomImageRecord(){
    }
}
