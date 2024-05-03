package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.DataDTO.RoomDTO;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Repository.RoomRepository;

import java.util.Date;
import java.util.List;

public interface RoomServices{

    Room saveRoom(Room room);
    List<Room> findAvailableRoomForBooking(String hotelID, String checkingDate, String checkoutDate);
    List<Room> findAllRoomsByHotelID(String hotelID);
    Room deleteRoomById(String roomID);
    Room findRoomByID(String roomID);
    boolean isAvailableRoom(String roomID, Date checkinDate, Date checkoutDate);
    RoomDTO toDTO(Room room);
    List<RoomDTO> toDTO(List<Room> rooms);

}
