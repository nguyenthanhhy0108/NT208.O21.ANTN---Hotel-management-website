package com.example.hotel_management.Service;

import com.example.hotel_management.Model.BookedCapacity;

import java.util.List;

public interface BookedCapacityServices {
    /**
     * Find a capacity object by provided hotelID
     * @param hotelId: String
     * @return
     * BookedCapacity object
     */
    BookedCapacity findByHotelID(String hotelId);

    /**
     * Get all satisfied hotel name in search action
     * @param checkInIndex: int
     * @param checkOutIndex: int
     * @param numberOfPeople: int
     * @param country: String
     * @param option: int
     * @return
     * A names list
     */
    List<Object> getSatisfiedHotelNames(int checkInIndex, int checkOutIndex, int numberOfPeople, String country, int option);

    void delete(BookedCapacity bookedCapacity);
}
