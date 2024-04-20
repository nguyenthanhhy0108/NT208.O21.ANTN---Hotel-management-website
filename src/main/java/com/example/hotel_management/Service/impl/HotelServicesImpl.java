package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Repository.HotelRepository;
import com.example.hotel_management.Service.HotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelServicesImpl implements HotelServices {

    private final HotelRepository hotelRepository;

    /**
     * Dependency Injection
     * @param hotelRepository: HotelRepository object
     */
    @Autowired
    public HotelServicesImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * Implement save hotel
     * @param hotel: Hotel object
     * @return
     * Hotel object which was saved
     */
    @Override
    public Hotel saveHotel(Hotel hotel) {
        return this.hotelRepository.save(hotel);
    }

    /**
     * Implement delete hotel
     * @param hotelId: HotelID which will be eliminated
     */
    @Transactional
    @Override
    public void deleteByHotelId(String hotelId) {
        List<Hotel> listHotel = hotelRepository.findByHotelID(hotelId);

        Hotel deletedHotel = listHotel.get(0);

        this.hotelRepository.delete(deletedHotel);
    }

    /**
     * Implement findAllHotels
     * @return
     * A list of Hotel object
     */
    @Override
    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }
}
