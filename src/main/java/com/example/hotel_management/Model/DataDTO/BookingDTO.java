package com.example.hotel_management.Model.DataDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BookingDTO {

    private int bookingId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkInDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkOutDate;

    private String customer;

    private String hotelId;

    private String roomId;

    private double totalPrice;

    private int isAccepted;

    private int isPaid;

    public BookingDTO(int bookingId,
                      Date checkInDate,
                      Date checkOutDate,
                      String customer,
                      String hotelId,
                      String roomId,
                      double totalPrice,
                      int isAccepted,
                      int isPaid) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.customer = customer;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
        this.isAccepted = isAccepted;
        this.isPaid = isPaid;
    }
}
