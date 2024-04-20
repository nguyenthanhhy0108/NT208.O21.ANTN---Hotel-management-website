package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;

//This class is match with HOTEL table in database
@Data
@Entity(name = "HOTEL")
public class Hotel {
    @Id
    @Column(name = "hotel_id")
    private String hotelID;
    @Column(name = "owner_username")
    private String ownerUsername;

    @OneToOne(mappedBy = "hotel",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.REMOVE
            })
    private HotelDetails hotelDetails;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "owner_username",
            insertable = false,
            updatable = false)
    private Users users;

    /**
     * Constructor
     * @param hotelID: String
     * @param ownerUsername: String
     */
    public Hotel(String hotelID, String ownerUsername) {
        this.hotelID = hotelID;
        this.ownerUsername = ownerUsername;
    }

    public Hotel() {
    }
}
