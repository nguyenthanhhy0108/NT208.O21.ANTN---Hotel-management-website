package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.Room;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    Booking findById(String id);

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

    boolean isValidBooking(String roomId, int num_people, String checkingDate, String checkoutDate);

    // void acceptBooking(String roomId, int num_people, String checkingDate, String checkoutDate, int price);

    /**
     * Find all hotel by provided customer
     * @param customer: String
     * @return
     * A list booking object
     */
    List<Booking> findByCustomer(String customer);

    /**
     * Find hotel by provided hotel ID
     * @param hotelId: String
     * @return
     * A List of booking objects
     */
    List<Booking> findByHotelId(String hotelId);

    /**
     * Find hotel by provided list hotel ID
     * @param hotelIds: List String
     * @return
     * A list of booking objects
     */
    List<Booking> findByHotelId(List<String> hotelIds);
}
