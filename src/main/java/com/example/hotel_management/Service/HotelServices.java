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

    /**
     * Get a list Hotel object with provided ownerUsername
     * @param ownerUsername: String
     * @return
     * A list Hotel object
     */
    List<Hotel> findByOwnerUsername(String ownerUsername);

    /**
     * Get a list Hotel object with provided hotelID
     * @param hotelID: String
     * @return
     * A list Hotel object
     */
    List<Hotel> findByHotelID(String hotelID);


    /**
     * Get a list Hotel object with provided isActive
     * @param isActive : int (-1:rejected, 0:waiting, 1:accepted)
     * @return A list Hotel object
     */
    List<Hotel> findByIsActive(int isActive);
}
