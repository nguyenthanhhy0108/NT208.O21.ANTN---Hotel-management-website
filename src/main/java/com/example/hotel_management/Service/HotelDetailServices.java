package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Hotel_detail;

import java.util.List;
import java.util.Optional;

public interface HotelDetailServices {
    List<Hotel_detail> findAll();
    Hotel_detail findById(String id);
    void save(Hotel_detail hotel_detail);
    void delete(String id);
}
