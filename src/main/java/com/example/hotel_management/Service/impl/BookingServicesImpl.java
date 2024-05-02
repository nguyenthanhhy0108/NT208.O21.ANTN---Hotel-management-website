package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.DataDTO.BookingDTO;
import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Repository.BookingRepository;
import com.example.hotel_management.Service.BookingServices;
import com.example.hotel_management.Service.HotelDetailsServices;
import com.example.hotel_management.Service.HotelServices;
import com.example.hotel_management.Service.RoomServices;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServicesImpl implements BookingServices {
    final BookingRepository bookingRepository;
    final RoomServices roomServices;
    final HotelServices hotelServices;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public BookingServicesImpl(BookingRepository bookingRepository, RoomServices roomServices, HotelServices hotelServices) {
        this.bookingRepository = bookingRepository;
        this.roomServices = roomServices;
        this.hotelServices = hotelServices;
    }

    @Transactional
    @Override
    public void updateBookedCapacityExecute(String hotelID, String updateColumn, int value) {
        String sql = "UPDATE HOTEL_CAPACITY_BOOKED SET " + updateColumn + " = " + updateColumn + " + " + value + " WHERE hotel_id = :hotelId";
        entityManager.createNativeQuery(sql)
                .setParameter("hotelId", hotelID)
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
        if (!isValidBooking(booking)){
            return null;
        }

        Room requestRoom = roomServices.findRoomByID(booking.getRoomId());
        requestRoom.setBookedGuests(requestRoom.getBookedGuests() + 1);
        roomServices.saveRoom(requestRoom);

        booking = this.updateBookedCapacityForSave(booking);

        bookingRepository.save(booking);
        return booking;
    }

    @Transactional
    @Override
    public Booking delete(Booking theBooking){
        bookingRepository.delete(theBooking);

        // increase booked guest
        Room requestRoom = roomServices.findRoomByID(theBooking.getRoomId());
        requestRoom.setBookedGuests(requestRoom.getBookedGuests() - 1);
        roomServices.saveRoom(requestRoom);

        this.updateBookedCapacityForDelete(theBooking);

        return theBooking;
    }

    @Transactional
    @Override
    public Booking deleteByID(String bookingID) {
        Booking deleteBooking = this.findById(bookingID);

        if (deleteBooking == null){
            return null;
        }

        // decrease the booked guest as the booking is deleted
        Room requestRoom = roomServices.findRoomByID(deleteBooking.getRoomId());
        requestRoom.setBookedGuests(requestRoom.getBookedGuests() - 1);

        roomServices.saveRoom(requestRoom);

        //consider delete and delete by id
        bookingRepository.deleteById(bookingID);
        this.updateBookedCapacityForDelete(deleteBooking);

        return  deleteBooking;
    }

    @Override
    @Transactional
    public Booking updateBookedCapacityForSave(Booking theBooking){
        Room requestRoom = roomServices.findRoomByID(theBooking.getRoomId());

        Date bookTime = new Date();
        int now = (int) (bookTime.getTime() / 1000 / 3600 / 24);

        int checkinDateCount =  (int) (theBooking.getCheckInDate().getTime() / 1000 / 3600 / 24) - now;
        int checkoutDateCount = (int) (theBooking.getCheckOutDate().getTime() / 1000 / 3600 / 24) - now;

        for (int i = checkinDateCount; i < checkoutDateCount + 1; i++){
            this.updateBookedCapacityExecute(theBooking.getHotelId(), "day" + i, requestRoom.getNumPeople());
        }

        theBooking.setBookDate(bookTime);
        theBooking.setTotalPrice((checkoutDateCount - checkinDateCount + 1) * requestRoom.getPrice());

        return theBooking;
    }

    @Override
    @Transactional
    public void updateBookedCapacityForDelete(Booking theBooking){
        Room requestRoom = roomServices.findRoomByID(theBooking.getRoomId());

        int now = (int) ((new Date()).getTime() / 1000 / 3600 / 24);
        int bookDateCount = (int) (theBooking.getBookDate().getTime() / 1000 / 3600 / 24) - now;
        int checkinDateCount =  (int) (theBooking.getCheckInDate().getTime() / 1000 / 3600 / 24) - now;
        int checkoutDateCount = (int) (theBooking.getCheckOutDate().getTime() / 1000 / 3600 / 24) - now;

        for (int i = checkinDateCount; i < checkoutDateCount + 1; i++){
            int updateDate = i - bookDateCount;
            this.updateBookedCapacityExecute(theBooking.getHotelId(), "day" + updateDate, -requestRoom.getNumPeople());
        }
    }

    /**
     * Implement find by customer
     * @param customer: String
     * @return
     * A List booking objects
     */
    @Override
    public List<Booking> findByCustomer(String customer) {
        return this.bookingRepository.findByCustomer(customer);
    }

    /**
     * Implement find by hotel ID
     * @param hotelId: String
     * @return
     * A list of booking objects
     */
    @Override
    public List<Booking> findByHotelId(String hotelId) {
        return this.bookingRepository.findByHotelId(hotelId);
    }

    /**
     * Implement find by a list hotel ID
     * @param hotelIds: List String
     * @return
     * A list of bookings objects
     */
    @Override
    public List<Booking> findByHotelId(List<String> hotelIds) {
        List<Booking> result = new ArrayList<>();
        for (String id : hotelIds) {
            List<Booking> bookings = this.findByHotelId(id);
            result.addAll(bookings);
        }
        return result;
    }

    /**
     * Implement convert to DTO
     * @param bookings: List
     * @return
     * List booking DTO
     */
    @Override
    public List<BookingDTO> convertToDTO(List<Booking> bookings) {
        List<BookingDTO> result = new ArrayList<>();
        for(Booking booking : bookings){
            BookingDTO bookingDTO = new BookingDTO(
                    booking.getBookingId(),
                    booking.getCheckInDate(),
                    booking.getCheckOutDate(),
                    booking.getCustomer(),
                    booking.getHotelId(),
                    booking.getRoomId(),
                    booking.getTotalPrice(),
                    booking.getIsAccepted());

            result.add(bookingDTO);
        }
        return result;
    }

    @Override
    public boolean isValidBooking(Booking theBooking){
        String requestedHotelID = theBooking.getHotelId();
        Date checkingDate = (Date) theBooking.getCheckInDate();
        Date checkoutDate = (Date) theBooking.getCheckOutDate();

        List<Room> checkValid = this.roomServices.validRequestRooms(theBooking.getRoomId(), checkingDate, checkoutDate);

        if (checkValid.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

//    @Override
//    public Booking assignRoomForBooking(Booking theBooking, int numPeople){
//        String requestedHotelID = theBooking.getHotelId();
//
//        Date checkingDate = theBooking.getCheckInDate();
//        Date checkoutDate = theBooking.getCheckOutDate();
//
//        List<Room> availableRooms = this.roomServices.findAvailableRoomForBooking(requestedHotelID, numPeople, checkingDate, checkoutDate);
//
//        if (availableRooms.isEmpty()){
//            return null;
//        }
//        else{
//            theBooking.setRoomId(availableRooms.get(0).getRoomID());
//            theBooking.setRoom(availableRooms.get(0));
//            return  theBooking;
//        }
//    }

    @Override
    @Transactional
    public Booking acceptBookingById(String bookingId, String Username){
        Booking requestBooking = this.findById(bookingId);

        List<Hotel> hotels = hotelServices.findByHotelID(requestBooking.getHotelId());

        if (hotels.isEmpty()){
            return null;
        }
        else {
            Hotel hotel = hotels.get(0);
            if (hotel.getOwnerUsername().equals(Username)){
                requestBooking.setIsAccepted(1);
                bookingRepository.save(requestBooking);
                return requestBooking;
            }
            else{
                return null;
            }
        }
    }

    @Override
    @Transactional
    public Booking refuseBookingById(String bookingId, String Username){
        Booking requestBooking = this.findById(bookingId);

        List<Hotel> hotels = hotelServices.findByHotelID(requestBooking.getHotelId());

        if (requestBooking.getIsAccepted() != 0 && hotels.isEmpty()){
            return null;
        }
        else {
            Hotel hotel = hotels.get(0);
            if (hotel.getOwnerUsername().equals(Username)){
                requestBooking.setTotalPrice(2);
                this.updateBookedCapacityForDelete(requestBooking);
                return bookingRepository.save(requestBooking);
            }
            else{
                return null;
            }
        }
    }
}