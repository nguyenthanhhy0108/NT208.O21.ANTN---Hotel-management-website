package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.DataDTO.BookingDTO;
import com.example.hotel_management.Model.Room;
import com.example.hotel_management.Repository.BookingRepository;
import com.example.hotel_management.Service.BookingServices;
import com.example.hotel_management.Service.RoomServices;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public BookingServicesImpl(BookingRepository bookingRepository, RoomServices roomServices) {
        this.bookingRepository = bookingRepository;
        this.roomServices = roomServices;
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
        Room requestRoom = roomServices.findRoomByID(booking.getRoomId());

        requestRoom.setBookedGuests(requestRoom.getBookedGuests() + 1);
        roomServices.saveRoom(requestRoom);

        int numOfDates = this.updateBookedCapacity(requestRoom.getHotelID(), booking.getCheckInDate(), booking.getCheckOutDate(), requestRoom.getNumPeople());
        booking.setTotalPrice(requestRoom.getPrice() * numOfDates);

        bookingRepository.save(booking);
        return booking;
    }

    @Transactional
    @Override
    public Booking delete(Booking theBooking){
        bookingRepository.delete(theBooking);
        Room requestRoom = roomServices.findRoomByID(theBooking.getRoomId());

        Date checkingDate = (Date) theBooking.getCheckInDate();
        Date checkoutDate = (Date) theBooking.getCheckOutDate();

        requestRoom.setBookedGuests(requestRoom.getBookedGuests() - 1);
        this.updateBookedCapacity(requestRoom.getHotelID(), checkingDate, checkoutDate, -requestRoom.getNumPeople());

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
    public int updateBookedCapacity(String hotelID, java.util.Date checkinDate , java.util.Date checkoutDate, int value){
        int now = (int) ((new Date()).getTime() / 1000 / 3600 / 24);
        int checkinDateCount =  (int) (checkinDate.getTime() / 1000 / 3600 / 24) - now;
        int checkoutDateCount = (int) (checkoutDate.getTime() / 1000 / 3600 / 24) - now;

        for (int i = checkinDateCount + 1; i <= checkoutDateCount + 1; i++){
            this.updateBookedCapacityExecute(hotelID, "day" + i, value);
        }
        return checkoutDateCount - checkinDateCount + 1;
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
            theBooking.setRoom(availableRooms.get(0));
            return  theBooking;
        }
    }
}