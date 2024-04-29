package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Repository.RoomRepository;

import java.util.List;

public interface RoomServices{

    Room saveRoom(Room room);
    List<Room> findAvailableRoomForBooking(String hotelID, int num_people, String checkingDate, String checkoutDate);
    List<Room> findAllRoomsByHotelID(String hotelID);
    Room deleteRoomById(String roomID);
    Room findRoomByID(String roomID);
}
