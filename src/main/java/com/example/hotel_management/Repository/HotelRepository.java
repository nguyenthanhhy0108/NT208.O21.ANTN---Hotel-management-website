package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Interact with Database
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    /**
     * Get a list Hotel object with provided hotelID
     * @param hotelID: String
     * @return
     * A list Hotel object
     */
    List<Hotel> findByHotelID(String hotelID);
}
