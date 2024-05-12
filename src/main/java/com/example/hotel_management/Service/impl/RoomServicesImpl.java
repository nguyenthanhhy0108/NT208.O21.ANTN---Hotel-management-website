package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.DataDTO.RoomDTO;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Repository.RoomRepository;
import com.example.hotel_management.Service.HotelServices;
import com.example.hotel_management.Service.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RoomServicesImpl implements RoomServices {
    private final RoomRepository roomRepository;
    private final HotelServices hotelServices;
    @Autowired
    RoomServicesImpl(RoomRepository roomRepository, HotelServices hotelServices){
        this.roomRepository = roomRepository;
        this.hotelServices = hotelServices;
    }

    @Override
    public Room saveRoom(Room room) {
        return this.roomRepository.save(room);
    }

    @Transactional
    @Override
    public Room deleteRoomById(String roomID) {
        Room roomToDelete = this.findRoomByID(roomID);
        this.roomRepository.deleteById(roomID);
        return roomToDelete;
    }

    @Override
    public List<Room> findAllRoomsByHotelID(String hotelID) {
        return roomRepository.findAllRoomByHotelID(hotelID);
    }

    @Override
    public List<Room> findAvailableRoomForBooking(String hotelID, String checkinDate, String checkoutDate){
        return roomRepository.findAvailableRoomForBooking(hotelID, checkinDate, checkoutDate);
    };

    @Override
    public boolean isAvailableRoom(String roomID, Date checkinDate, Date checkoutDate){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);

        String checkinDateString = df.format(checkinDate);
        String checkoutDateString = df.format(checkoutDate);

        return !roomRepository.validRequestRooms(roomID, checkinDateString, checkoutDateString).isEmpty();
    }

    @Override
    public RoomDTO toDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomID(room.getRoomID());
        roomDTO.setNumOfPeople(room.getNumPeople());
        roomDTO.setPrice(room.getPrice());
        return roomDTO;
    }

    @Override
    public List<RoomDTO> toDTO(List<Room> rooms) {
        List<RoomDTO> roomDTOs = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOs.add(toDTO(room));
        }
        return roomDTOs;
    }

    /**
     * Implement find by room ID
     * @param roomID String
     * @return
     * Room object
     */
    @Override
    public Room findByRoomID(String roomID) {
        return this.roomRepository.findByRoomID(roomID);
    }

    @Override
    public Room findRoomByID(String roomID){
        Optional<Room> roomOptional = roomRepository.findById(roomID);

        // Check if the room exists
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            return room;
        } else {
            return null;
        }
    }

    @Override
    public String findUserNameByRoomID(String roomID){
        Room room = this.roomRepository.findByRoomID(roomID);
        String hotelID = room.getHotelID();
        Hotel hotel = hotelServices.findByHotelID(hotelID).get(0);
        return hotel.getOwnerUsername();
    }
}