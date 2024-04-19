package com.example.hotel_management.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="BOOKING")
public class Booking {
    @Id
    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "check_in_date")
    private Date checkInDate;

    @Column(name = "check_out_date")
    private Date checkOutDate;

    @Column(name = "customer")
    private String customer;

    @Column(name = "hotel_id")
    private String hotelId;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "total_price")
    private double totalPrice;

    /**
     * Constructor
     * @param bookingId booking id (Integer)
     * @param checkInDate check in date (Date)
     * @param checkOutDate check out date (Date)
     * @param customer username of customer (String)
     * @param hotelId id of booking hotel (String)
     * @param roomId id of booking room (String)
     * @param totalPrice total price (double)
     */
    public Booking(Integer bookingId,
                   Date checkInDate,
                   Date checkOutDate,
                   String customer,
                   String hotelId,
                   String roomId,
                   double totalPrice) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.customer = customer;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
    }
    public Booking() {}
}
