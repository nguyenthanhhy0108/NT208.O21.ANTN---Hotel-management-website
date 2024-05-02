package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.DataDTO.BookingDTO;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.Room;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingServices {
    /**
     * Find all booking item
     * @return List of BookingDTO
     */
    List<Booking> findAll();

    /**
     * Find the booking by id
     * @param id id of the booking (Integer)
     * @return BookingDTO
     */
    Booking findById(String id);

    /**
     * Save the chosen BookingDTO
     * @param booking (BookingDTO)
     * @return the saved BookingDTO
     */
    Booking save(Booking booking);

    /**
     * Delete the chosen BookingDTO
     * @param bookingID (String)
     */
    Booking deleteByID(String bookingID);

    Booking delete(Booking theBooking);

    // Booking assignRoomForBooking(Booking theBooking, int People);

    void updateBookedCapacityExecute(String roomID, String updateColumn, int value);
    Booking updateBookedCapacityForSave(Booking theBooking);
    void updateBookedCapacityForDelete(Booking theBooking);
    // void acceptBooking(String roomId, int num_people, String checkingDate, String checkoutDate, int price);
    Booking acceptBookingById(String bookingId, String Username);
    Booking refuseBookingById(String bookingId, String Username);
    Booking completeBookingById(String bookingId, String Username);
    Booking deleteForRefuseAndCompleteBooking(String bookingID);
    boolean isValidBooking(Booking theBooking);
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

    /**
     * Convert booking list to DTO
     * @param bookings: List
     * @return
     * List booking DTO objects
     */
    List<BookingDTO> convertToDTO(List<Booking> bookings);
}
