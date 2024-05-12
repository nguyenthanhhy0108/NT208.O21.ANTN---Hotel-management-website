package com.example.hotel_management.Service;

import com.example.hotel_management.Model.HotelImageRecord;
import com.example.hotel_management.Model.RoomImageRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface HotelImageRecordServices {
    Map uploadSingleImage(MultipartFile file);
    HotelImageRecord uploadHotelImageUpdateDB(MultipartFile file, String hotelID);
}
