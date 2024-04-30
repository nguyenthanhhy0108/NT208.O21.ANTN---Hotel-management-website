package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Repository.BookingRepository;
import com.example.hotel_management.Repository.RoomRepository;
import com.example.hotel_management.Service.BookingServices;
import com.example.hotel_management.Service.RoomServices;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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
    private EntityManager entityManager;

    @Autowired
    public BookingServicesImpl(BookingRepository bookingRepository, RoomServices roomServices) {
        this.bookingRepository = bookingRepository;
        this.roomServices = roomServices;
    }

    @Transactional
    @Override
    public void updateBookedCapacityExecute(String roomID, String updateColumn, int value) {
        String sql = "UPDATE HOTEL_CAPACITY_BOOKED SET " + updateColumn + " = " + updateColumn + " + " + value + " WHERE room_id = :roomId";
        entityManager.createNativeQuery(sql)
                .setParameter("roomId", roomID)
                .executeUpdate();
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
            return booking;
        }
        else {
            return null;
        }
    }

    @Transactional
    @Override
    public Booking save(Booking booking) {
        bookingRepository.save(booking);
        this.updateBookedCapacity(booking.getRoomId(), booking.getCheckInDate(), booking.getCheckOutDate(), 1);
        return booking;
    }

    @Transactional
    @Override
    public Booking delete(Booking theBooking){
        bookingRepository.delete(theBooking);

        Date checkingDate = (Date) theBooking.getCheckInDate();
        Date checkoutDate = (Date) theBooking.getCheckOutDate();
        this.updateBookedCapacity(theBooking.getRoomId(),checkingDate, checkoutDate, -1);

        return theBooking;
    }

    @Transactional
    @Override
    public Booking deleteByID(String bookingID) {
        Booking deleteBooking = this.findById(bookingID);
        Room requestRoom = roomServices.findRoomByID(deleteBooking.getRoomId());

        if(deleteBooking == null){
            return null;
        }

        //consider delete and delete by id
        bookingRepository.deleteById(bookingID);
        Date checkingDate = (Date) deleteBooking.getCheckInDate();
        Date checkoutDate = (Date) deleteBooking.getCheckOutDate();
        this.updateBookedCapacity(deleteBooking.getRoomId(), checkingDate, checkoutDate, -requestRoom.getNumPeople());

        return  deleteBooking;
    }

    @Override
    @Transactional
    public void updateBookedCapacity(String roomID, java.util.Date checkinDate , java.util.Date checkoutDate, int value){
        Room requestRoom = roomServices.findRoomByID(roomID);

        int daysBetween = (int) (checkoutDate.getTime() / 1000 / 3600 / 24) - (int) (checkinDate.getTime() / 1000 / 3600 / 24);

        for (long i = 1; i < daysBetween + 1; i++){
            this.updateBookedCapacityExecute(roomID, "day" + i, requestRoom.getNumPeople());
        }
    }

    @Override
    public boolean isValidBooking(Booking theBooking, int numPeople){

        String requestedHotelID = theBooking.getHotelId();
        Date checkingDate = (Date) theBooking.getCheckInDate();
        Date checkoutDate = (Date) theBooking.getCheckOutDate();

        List<Room> availableRooms = this.roomServices.findAvailableRoomForBooking(requestedHotelID, numPeople, checkingDate, checkoutDate);

        if (availableRooms.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public Booking assignRoomForBooking(Booking theBooking, int numPeople){
        String requestedHotelID = theBooking.getHotelId();

        Date checkingDate = (Date) theBooking.getCheckInDate();
        Date checkoutDate = (Date) theBooking.getCheckOutDate();

        List<Room> availableRooms = this.roomServices.findAvailableRoomForBooking(requestedHotelID, numPeople, checkingDate, checkoutDate);

        if (availableRooms.isEmpty()){
            return null;
        }
        else{
            theBooking.setRoomId(availableRooms.get(0).getRoomID());
            return  theBooking;
        }
    }
}