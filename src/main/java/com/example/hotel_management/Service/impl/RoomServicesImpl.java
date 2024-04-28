package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Repository.RoomRepository;
import com.example.hotel_management.Service.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void deleteByRoomId(String roomId) {
        // Find the room by its ID
        Optional<Room> Room = roomRepository.findById(roomId);

        // Check if the room exists
        if (Room.isPresent()) {
            Room roomToDelete = Room.get();

            // Delete the room
            roomRepository.delete(roomToDelete);
        } else {
            // Handle the case when the room with the given ID does not exist
            throw new NoSuchElementException("Room with ID " + roomId + " not found");
        }
    }

    @Override
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findAvailableRoomForBooking(String hotelID, int num_people, String checkingDate, String checkoutDate){
        return roomRepository.findAvailableRoomForBooking(hotelID, num_people, checkingDate, checkoutDate);
    };
}