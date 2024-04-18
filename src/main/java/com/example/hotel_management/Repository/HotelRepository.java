package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> findByHotelID(String hotelID);
}
