package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="BOOKING")
public class Booking {
    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @Column(name = "check_in_date")
    @Temporal(TemporalType.DATE)
    private Date checkInDate;

    @Temporal(TemporalType.DATE)
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

    @Column(name="is_accepted")
    private int isAccepted;


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
    public Booking(int bookingId,
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

    public Booking() {this.isAccepted = 0;}
}
