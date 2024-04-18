package com.example.hotel_management.Service;

import com.example.hotel_management.Model.HotelDetails;

import java.util.List;

public interface HotelDetailsServices {
    List<HotelDetails> findAll();
    HotelDetails findById(String id);
    HotelDetails save(HotelDetails hotel_detail);
    void delete(String id);
}
