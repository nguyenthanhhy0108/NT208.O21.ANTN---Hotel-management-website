package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.HotelImageRecord;
import com.example.hotel_management.Model.RoomImageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelImageRecordRepository extends JpaRepository<HotelImageRecord, String> {
    List<HotelImageRecord> findHotelImageRecordByHotelID(String roomID);
}