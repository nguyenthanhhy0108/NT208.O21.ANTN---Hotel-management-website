package com.example.hotel_management.Service.impl;

import com.cloudinary.Cloudinary;
import com.example.hotel_management.Model.HotelImageRecord;
import com.example.hotel_management.Model.RoomImageRecord;
import com.example.hotel_management.Repository.HotelImageRecordRepository;
import com.example.hotel_management.Repository.RoomImageRecordRepository;
import com.example.hotel_management.Service.HotelImageRecordServices;
import com.example.hotel_management.Service.RoomImageRecordServices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HotelImageRecordServicesImpl implements HotelImageRecordServices {
    private final Cloudinary cloudinary;
    private final HotelImageRecordRepository hotelImageRecordRepository;

    @Transactional
    @Override
    public Map uploadSingleImage(MultipartFile file){
        try{
            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
        }catch (IOException io){
            throw new RuntimeException("Image upload fail");
        }
    }

    @Transactional
    @Override
    public HotelImageRecord uploadHotelImageUpdateDB(MultipartFile file, String hotelID){
        Map imageInfo = this.uploadSingleImage(file);
        HotelImageRecord uploadedRecord = new HotelImageRecord();
        uploadedRecord.setHotelID(hotelID);
        uploadedRecord.setURL((String) imageInfo.get("url"));

        hotelImageRecordRepository.save(uploadedRecord);
        return uploadedRecord;
    }

}
