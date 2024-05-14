package com.example.hotel_management.Service;

import com.example.hotel_management.Model.DataDTO.HotelImageDTO;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.HotelImageRecord;
import com.example.hotel_management.Model.RoomImageRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface HotelImageRecordServices {
    Map uploadSingleImage(MultipartFile file);
    HotelImageRecord uploadHotelImageUpdateDB(MultipartFile file, String hotelID);
    List<HotelImageRecord> findHotelImageRecordsByHotelID(String hotelID);
    String findAvatarByHotelID(String hotelID);
    HotelImageDTO convertToDTO(List<HotelImageRecord> hotelImageRecords);
    HotelImageDTO getDTOByHotelID(String hotelID);
    void deleteImage(String publicId);
    void deleteHotelImageUpdateDB(String publicURL);
    HotelImageRecord findByURL(String publicURL);
}
