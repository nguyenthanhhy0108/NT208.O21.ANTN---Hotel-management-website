package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Room {
    @Id
    @Column(name = "room_id")
    private String roomID;

    @Column(name = "hotel_id")
    private String hotelID;

    @Column(name = "num_people")
    private int numPeople;

    @Column(name = "price")
    private float price;

    @Column(name = "is_booked")
    private boolean isBooked;

    @Column(name = "booked_guests")
    private int bookedGuests;

    @Column(name = "star_rating")
    private String starRating;

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


    public Room(String roomID, String hotelID, int numPeople, float price, boolean isBooked, int bookedGuests, String starRating, Hotel hotel) {
        this.roomID = roomID;
        this.hotelID = hotelID;
        this.numPeople = numPeople;
        this.price = price;
        this.isBooked = isBooked;
        this.bookedGuests = bookedGuests;
        this.starRating = starRating;
        this.hotel = hotel;
    }
}
