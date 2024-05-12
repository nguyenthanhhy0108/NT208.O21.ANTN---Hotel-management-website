package com.example.hotel_management.Service;

import com.example.hotel_management.Model.DataDTO.RoomImageDTO;
import com.example.hotel_management.Model.RoomImageRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface RoomImageRecordServices {
    Map uploadSingleImage(MultipartFile file);
    RoomImageRecord uploadRoomImageUpdateDB(MultipartFile file, String roomID);
    List<RoomImageRecord> findRoomImageRecordsByRoomID(String roomID);
    RoomImageDTO convertToDTO(List<RoomImageRecord> roomImageRecords);
    RoomImageDTO getDTOByRoomID(String roomID);
    void deleteImage(String publicId);
    void deleteRoomImageUpdateDB(String publicURL);
    RoomImageRecord findByURL(String publicURL);
}
