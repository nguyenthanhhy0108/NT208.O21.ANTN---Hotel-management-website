package com.example.hotel_management.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name = "HOTEL_DETAIL")
public class HotelDetails {
    @Id
    @Column(name = "hotel_id")
    private String hotelID;

    @Column(name = "intro")
    @Size(min = 0, max = 255, message = "Introduction size must be between 0 and 255 characters")
    private String intro;

    @Column(name = "name")
    @Size(min = 1, max = 50, message = "Hotel name must be between 1 and 50 characters")
    private String name;

    @Column(name = "country")
    @Size(min = 0, max = 50, message = "Country must be smaller than 50 characters")
    private String country;

    @Column(name = "province")
    @Size(min = 0, max = 50, message = "Province must be smaller than 50 characters")
    private String province;

    @Column(name = "city")
    @Size(min = 0, max = 50, message = "City must be smaller than 50 characters")
    private String city;

    @Column(name = "street")
    @Size(min = 0, max = 50, message = "Street must be smaller than 50 characters")
    private String street;

    @Column(name = "house_number")
    @Size(min = 0, max = 50, message = "House number must be smaller than 50 characters")
    private String houseNumber;

    @Column(name = "phone_number")
    @Pattern(regexp = "^[0-9]{10}", message = "Invalid phone number")
    private String phoneNumber;

    @Column(name = "area")
    @Min(value = 0, message = "Area must be greater than or equal to 0")
    private float area;

    @Column(name = "booking_count")
    private int bookingCount;

    @Column(name = "total_capacity")
    private long totalCapacity;

    @Column(name = "price_per_person")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
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
