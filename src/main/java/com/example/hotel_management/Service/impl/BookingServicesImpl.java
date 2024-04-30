package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Repository.BookingRepository;
import com.example.hotel_management.Repository.RoomRepository;
import com.example.hotel_management.Service.BookingServices;
import com.example.hotel_management.Service.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookingServicesImpl implements BookingServices {
    final BookingRepository bookingRepository;
    final RoomServices roomServices;

    @Autowired
    public BookingServicesImpl(BookingRepository bookingRepository, RoomServices roomServices) {
        this.bookingRepository = bookingRepository;
        this.roomServices = roomServices;
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
    public Booking delete(Booking theBooking){
        bookingRepository.delete(theBooking);
        return theBooking;
    }

    @Override
    public Booking deleteByID(String bookingID) {
        Booking deleteBooking = this.findById(bookingID);
        //consider delete and delete by id
        bookingRepository.deleteById(bookingID);

        return  deleteBooking;
    }


    @Override
    public boolean isValidBooking(Booking theBooking){
        String roomId = theBooking.getRoomId();
        Room requestRoom = this.roomServices.findRoomByID(roomId);

        Hotel requestedHotel = requestRoom.getHotel();
        int num_people = requestRoom.getNumPeople();
        Date checkingDate = (Date) theBooking.getCheckInDate();
        Date checkoutDate = (Date) theBooking.getCheckOutDate();

        List<Room> availableRooms = this.roomServices.findAvailableRoomForBooking(requestRoom.getHotelID(), num_people, checkingDate, checkoutDate);

        if (availableRooms.contains(requestRoom)){
            return true;
        }
        else{
            return false;
        }
    }
}
