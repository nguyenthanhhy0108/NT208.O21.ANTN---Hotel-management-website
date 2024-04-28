package com.example.hotel_management.Model.DataDTO;

import com.example.hotel_management.Model.Booking;
import lombok.Data;

import java.util.List;

@Data
public class WaitingRequest {
    private boolean roleOwner;
    private List<Booking> receivedBookings;
    private List<Booking> sentBookings;
    private List<String> receivedHotelNames;
    List<String> sentHotelNames;

    public WaitingRequest(boolean roleOwner,
                          List<Booking> receivedBookings,
                          List<Booking> sentBookings,
                          List<String> receivedHotelNames,
                          List<String> sentHotelNames) {
        this.roleOwner = roleOwner;
        this.receivedBookings = receivedBookings;
        this.sentBookings = sentBookings;
        this.receivedHotelNames = receivedHotelNames;
        this.sentHotelNames = sentHotelNames;
    }

    public WaitingRequest() {
    }
}
