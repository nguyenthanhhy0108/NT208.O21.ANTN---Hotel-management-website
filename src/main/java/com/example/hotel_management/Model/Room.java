package com.example.hotel_management.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "ROOM")
public class Room {
    public static int count = 50;

    @Id
    @Column(name = "room_id")
    @Size(min = 1, max = 50, message = "Room ID must be between 1 and 50 characters")
    private String roomID;

    @Column(name = "hotel_id")
    private String hotelID;

    @Column(name = "num_people")
    @Min(value = 0, message = "Maximum number of people must be greater than or equal to 0")
    private int numPeople;

    @Column(name = "price")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private float price;

    @Column(name = "booked_guests")
    private int bookedGuests;

    @Column(name = "star_rating")
    private float starRating;

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

    @OneToMany(mappedBy = "room", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Booking> bookings;

    @OneToMany(mappedBy = "room", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<RoomImageRecord> roomImageRecords;

    public Room(String hotelID, int numPeople, float price, int bookedGuests, float starRating, Hotel hotel) {
        this.hotelID = hotelID;
        this.numPeople = numPeople;
        this.price = price;
        this.bookedGuests = bookedGuests;
        this.starRating = starRating;
        this.hotel = hotel;
        count++;
        this.roomID = String.valueOf(count);
    }

    public Room(){
        this.bookedGuests = 0;
        this.starRating=0;
        count++;
        this.roomID = String.valueOf(count);
    }
}
