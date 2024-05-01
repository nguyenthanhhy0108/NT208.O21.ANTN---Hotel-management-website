package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, String>{
    @Query(value= "SELECT * FROM Room checking_rooms WHERE checking_rooms.room_id " +
            "NOT IN (select room_id from booking " +
            "WHERE NOT (check_in_date > ?4 OR check_out_date < ?3))" +
            "AND checking_rooms.num_people = ?2 AND checking_rooms.hotel_id = ?1", nativeQuery=true)
    List<Room> findAvailableRoomForBooking(String hotelID, int num_people, Date checkingDate, Date checkoutDate);

    @Query(value = "SELECT * FROM Room WHERE  hotel_id = ?1", nativeQuery = true)
    List<Room> findAllRoomByHotelID(String hotelID);
}
