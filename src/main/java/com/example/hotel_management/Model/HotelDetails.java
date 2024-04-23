package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "HOTEL_DETAIL")
public class HotelDetails {
    @Id
    @Column(name = "hotel_id")
    private String hotelID;

    @Column(name = "intro")
    private String intro;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "area")
    private float area;

    @Column(name = "booking_count")
    private int bookingCount;

    @Column(name = "total_capacity")
    private long totalCapacity;

    @Column(name = "price_per_person")
    private long pricePerPerson;

    @OneToOne
    @JoinColumn(name = "hotel_id",
            insertable = false,
            updatable = false)
    private Hotel hotel;

    /**
     * Constructor
     * @param hotelID hotel id (String)
     * @param intro introduction (String)
     * @param name hotel name (String)
     * @param country country (String)
     * @param province province (String)
     * @param city city (String)
     * @param street street (String)
     * @param houseNumber house number (String)
     * @param phoneNumber phone number (String)
     * @param area area (Float)
     * @param bookingCount booking count (Integer)
     * @param totalCapacity total capacity (long)
     * @param pricePerPerson price per person (long)
     */
    public HotelDetails(String hotelID,
                        String intro,
                        String name,
                        String country,
                        String province,
                        String city,
                        String street,
                        String houseNumber,
                        String phoneNumber,
                        float area,
                        int bookingCount,
                        long totalCapacity,
                        long pricePerPerson) {
        this.hotelID = hotelID;
        this.intro = intro;
        this.name = name;
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.phoneNumber = phoneNumber;
        this.area = area;
        this.bookingCount = bookingCount;
        this.totalCapacity = totalCapacity;
        this.pricePerPerson = pricePerPerson;
    }

    /**
     * Get address of selected Hotel
     * @return a String with format: country, province, city, street, houseNumber
     */
    public String getAddress() {
        return country + ", " + province + ", " + city + ", " + street + ", " + houseNumber;
    }

    public HotelDetails() {
    }
}
