package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Hotel;

import java.util.List;

public interface HotelServices {
    /**
     * Save Hotel to database
     * @param hotel: Hotel object
     * @return
     * Hotel object which was saved
     */
    Hotel saveHotel(Hotel hotel);

    /**
     * Delete Hotel from database
     * @param hotelId: HotelID which will be eliminated
     */
    void deleteByHotelId(String hotelId);

    /**
     * Find all hotel from database
     * @return
     * A list of Hotel object
     */
    List<Hotel> findAllHotels();
}
