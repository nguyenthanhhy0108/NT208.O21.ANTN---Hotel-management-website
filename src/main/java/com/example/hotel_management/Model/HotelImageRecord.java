package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "HOTEL_IMAGE")
public class HotelImageRecord {
    @Id
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recordID;

    @Column(name = "URL")
    private String URL;

    @Column(name = "hotel_id")
    private String hotelID;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "hotel_id",
            insertable = false,
            updatable = false)
    private Hotel hotel;

    public HotelImageRecord(){
    }
}
