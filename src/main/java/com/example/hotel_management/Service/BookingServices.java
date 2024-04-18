package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Booking;

import java.util.List;

public interface BookingServices {
    List<Booking> findAll();
    Booking findById(Integer id);
    void save(Booking booking);
    void delete(Booking booking);
}
