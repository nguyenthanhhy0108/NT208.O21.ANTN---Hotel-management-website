package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,String> {
    /**
     * Find all booking by provided customer
     * @param customer: String
     * @return
     * A list booking objects
     */
    List<Booking> findByCustomer(String customer);

    /**
     * Find booking by provided hotel ID
     * @param hotelId: String
     * @return
     * A List of booking objects
     */
    List<Booking> findByHotelId(String hotelId);

    /**
     * Find booking by provided room id
     * @param roomId
     * @return A list booking objects
     */
    List<Booking> findByRoomId(String roomId);
}
