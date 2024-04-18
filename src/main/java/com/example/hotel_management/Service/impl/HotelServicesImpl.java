package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Repository.HotelRepository;
import com.example.hotel_management.Service.HotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HotelServicesImpl implements HotelServices {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelServicesImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return this.hotelRepository.save(hotel);
    }

    @Transactional
    @Override
    public void deleteByHotelId(String hotelId) {
        List<Hotel> listHotel = hotelRepository.findByHotelID(hotelId);

        Hotel deletedHotel = listHotel.get(0);

        this.hotelRepository.delete(deletedHotel);
    }
}
