package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Hotel;

public interface HotelServices {
    Hotel saveHotel(Hotel hotel);

    void deleteByHotelId(String hotelId);
}
