package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Repository.RoomRepository;
import com.example.hotel_management.Service.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RoomServicesImpl implements RoomServices {
    private final RoomRepository roomRepository;

    @Autowired RoomServicesImpl(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
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
    public List<Room> findAvailableRoomForBooking(String hotelID, int num_people, Date checkingDate, Date checkoutDate){
        return roomRepository.findAvailableRoomForBooking(hotelID, num_people, checkingDate, checkoutDate);
    };

    @Override
    public Room findRoomByID(String roomID){
        Optional<Room> Room = roomRepository.findById(roomID);

        // Check if the room exists
        if (Room.isPresent()) {
            Room room = Room.get();

            // Delete the room
            roomRepository.deleteById(room.getRoomID());
            return room;
        } else {
            // Handle the case when the room with the given ID does not exist
            throw new NoSuchElementException("Room with ID " + roomID + " not found");
        }
    }
}