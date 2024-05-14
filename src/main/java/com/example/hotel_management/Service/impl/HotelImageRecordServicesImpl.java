package com.example.hotel_management.Service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.hotel_management.Model.DataDTO.HotelImageDTO;
import com.example.hotel_management.Model.DataDTO.RoomImageDTO;
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
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
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

    @Override
    public List<HotelImageRecord> findHotelImageRecordsByHotelID(String hotelID){
        return this.hotelImageRecordRepository.findHotelImageRecordByHotelID(hotelID);
    }

    @Override
    public String findAvatarByHotelID(String hotelID) {
        List<HotelImageRecord> result = this.findHotelImageRecordsByHotelID(hotelID);
        if (result.isEmpty()){
            return "/images/room3.jpg";
        }
        else{
            return result.get(0).getURL();
        }
    }

    @Override
    public HotelImageDTO convertToDTO(List<HotelImageRecord> hotelImageRecords){
        HotelImageDTO hotelImageDTO = new HotelImageDTO();

        for (HotelImageRecord record : hotelImageRecords){
            hotelImageDTO.getImageURL().add(record.getURL());
        }

        return hotelImageDTO;
    }

    @Override
    public HotelImageDTO getDTOByHotelID(String hotelID){
        List<HotelImageRecord> hotelRecords = this.findHotelImageRecordsByHotelID(hotelID);
        return this.convertToDTO(hotelRecords);
    }

    @Transactional
    @Override
    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            System.err.println("Error deleting image: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteHotelImageUpdateDB(String publicURL){
        // Parse the URL
        URI uri = URI.create(publicURL);

        // Get the file name from the URL
        String fileNameWithExtension = Paths.get(uri.getPath()).getFileName().toString();

        // Get rid of the file extension
        String publicID = fileNameWithExtension.contains(".") ?
                fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.')) :
                fileNameWithExtension;

        HotelImageRecord deleteRecord = this.findByURL(publicURL);
        deleteImage(publicID);
        this.hotelImageRecordRepository.delete(deleteRecord);
    }

    @Override
    public HotelImageRecord findByURL(String publicURL) {
        return this.hotelImageRecordRepository.findByURL(publicURL);
    }
}
