package com.example.hotel_management.Model.DataDTO;

import lombok.Data;

import java.util.Date;

@Data
public class BookingDTO {

    private int bookingId;

    private Date checkInDate;

    private Date checkOutDate;

    private String customer;

    private String hotelId;

    private String roomId;

    private double totalPrice;

    private int isAccepted;

    public BookingDTO(int bookingId,
                      Date checkInDate,
                      Date checkOutDate,
                      String customer,
                      String hotelId,
                      String roomId,
                      double totalPrice,
                      int isAccepted) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.customer = customer;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
        this.isAccepted = isAccepted;
    }
}
