package com.example.hotel_management.Service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.hotel_management.Model.DataDTO.RoomImageDTO;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Model.RoomImageRecord;
import com.example.hotel_management.Repository.RoomImageRecordRepository;
import com.example.hotel_management.Service.RoomImageRecordServices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleInfoNotFoundException;
import java.io.IOException;
// import java.lang.foreign.SegmentScope;
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoomImageRecordServicesImpl implements RoomImageRecordServices {
    private final Cloudinary cloudinary;
    private final RoomImageRecordRepository roomImageRecordRepository;

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
    public RoomImageRecord uploadRoomImageUpdateDB(MultipartFile file, String roomID){
        Map imageInfo = this.uploadSingleImage(file);
        RoomImageRecord uploadedRecord = new RoomImageRecord();
        uploadedRecord.setRoomID(roomID);
        uploadedRecord.setURL((String) imageInfo.get("url"));

        roomImageRecordRepository.save(uploadedRecord);
        return uploadedRecord;
    }

    @Override
    public List<RoomImageRecord> findRoomImageRecordsByRoomID(String roomID){
        return this.roomImageRecordRepository.findByRoomID(roomID);
    }

    @Override
    public RoomImageDTO convertToDTO(List<RoomImageRecord> roomImageRecords){
        RoomImageDTO roomImageDTO = new RoomImageDTO();

        for (RoomImageRecord record : roomImageRecords){
            roomImageDTO.getImageURL().add(record.getURL());
        }

        return roomImageDTO;
    }

    @Override
    public RoomImageDTO getDTOByRoomID(String roomID){
        List<RoomImageRecord> imageRecords = this.findRoomImageRecordsByRoomID(roomID);
        return this.convertToDTO(imageRecords);
    }

    @Transactional
    @Override
    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            System.out.println("Image deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting image: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteRoomImageUpdateDB(String publicURL){
        // Parse the URL
        URI uri = URI.create(publicURL);

        // Get the file name from the URL
        String fileNameWithExtension = Paths.get(uri.getPath()).getFileName().toString();

        // Get rid of the file extension
        String publicID = fileNameWithExtension.contains(".") ?
                fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.')) :
                fileNameWithExtension;

        RoomImageRecord deleteRecord = this.roomImageRecordRepository.findByURL(publicURL);
        deleteImage(publicID);
        this.roomImageRecordRepository.delete(deleteRecord);
    }

    @Override
    public RoomImageRecord findByURL(String publicURL){
        return this.roomImageRecordRepository.findByURL(publicURL);
    }
}
