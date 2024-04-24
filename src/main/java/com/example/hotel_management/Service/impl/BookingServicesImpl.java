package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Repository.BookingRepository;
import com.example.hotel_management.Repository.RoomRepository;
import com.example.hotel_management.Service.BookingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookingServicesImpl implements BookingServices {
    final BookingRepository bookingRepository;
    final RoomRepository roomRepository
    @Autowired
    public BookingServicesImpl(BookingRepository bookingRepository, RoomRepository roomRepository) {

        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking findById(String id){
        Booking booking = null;
        Optional<Booking> result = bookingRepository.findById(id);

        if (result.isPresent()) {
            booking = result.get();
        }
        else {
            throw new RuntimeException("Did not find booking id - " + id);
        }
        return booking;
    }

    @Override
    public Booking save(Booking booking) {
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }


    @Override
    public boolean isValidBooking(String roomId, int num_people, String checkingDate, String checkoutDate){
        Optional<Room> Room = this.roomRepository.findById(roomId);
        Room requestRoom = null;

        if (Room.isPresent()){
            Room requestRoom = Room.get();
            requestRoom.getHotel()
        }
        else {
            // Handle the case when the room with the given ID does not exist
            throw new NoSuchElementException("Room with ID " + roomId + " not found");
        }

        List<Room> availableRooms = this.roomRepository.findAvailableRoomForBooking(requestRoom.getHotelID(), num_people, checkingDate, checkoutDate);

        if (availableRooms.contains(requestRoom)){
            return true;
        }

        return false;
    }

    public void createBooking(Booking bookingInfoRequest){

    }
}
