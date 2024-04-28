package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Repository.BookingRepository;
import com.example.hotel_management.Service.BookingServices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServicesImpl implements BookingServices {
    final BookingRepository bookingRepository;

    public BookingServicesImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
    @Override
    public Booking findById(Integer id){
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
}
