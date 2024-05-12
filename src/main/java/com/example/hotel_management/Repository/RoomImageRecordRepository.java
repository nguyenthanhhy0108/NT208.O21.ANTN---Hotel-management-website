package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.BookedCapacity;
import com.example.hotel_management.Model.RoomImageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomImageRecordRepository extends JpaRepository<RoomImageRecord, String> {
   List<RoomImageRecord> findByRoomID(String roomID);
   RoomImageRecord findByURL(String URL);
}