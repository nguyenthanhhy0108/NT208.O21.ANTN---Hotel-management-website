package com.example.hotel_management.Model.DataDTO;

import com.example.hotel_management.Model.Booking;
import lombok.Data;

import java.util.List;

@Data
public class WaitingRequest {
    private boolean roleOwner;
    private String customerName;
    private List<BookingDTO> receivedBookings;
    private List<BookingDTO> sentBookings;
    private List<String> receivedHotelNames;
    private List<String> receivedCustomerNames;
    private List<String> sentHotelNames;

    public WaitingRequest(boolean roleOwner,
                          String customerName,
                          List<BookingDTO> receivedBookings,
                          List<BookingDTO> sentBookings,
                          List<String> receivedHotelNames,
                          List<String> receivedCustomerNames,
                          List<String> sentHotelNames) {
        this.roleOwner = roleOwner;
        this.customerName = customerName;
        this.receivedBookings = receivedBookings;
        this.sentBookings = sentBookings;
        this.receivedHotelNames = receivedHotelNames;
        this.receivedCustomerNames = receivedCustomerNames;
        this.sentHotelNames = sentHotelNames;
    }

    public WaitingRequest() {
    }
}
