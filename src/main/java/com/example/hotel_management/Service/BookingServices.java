package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Booking;

import java.util.List;

public interface BookingServices {
    /**
     * Find all booking item
     * @return List of Booking
     */
    List<Booking> findAll();

    /**
     * Find the booking by id
     * @param id id of the booking (Integer)
     * @return Booking
     */
    Booking findById(Integer id);

    /**
     * Save the chosen Booking
     * @param booking (Booking)
     * @return the saved Booking
     */
    Booking save(Booking booking);

    /**
     * Delete the chosen Booking
     * @param booking (Booking)
     */
    void delete(Booking booking);
}
