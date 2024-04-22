package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.BookedCapacity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedCapacityRepository extends JpaRepository<BookedCapacity, String> {
    /**
     * Find a booking schedule by provided hotel ID
     * @param hotelId: String
     * @return
     * A booking capacity schedule
     */
    BookedCapacity findByHotelID(String hotelId);
}
