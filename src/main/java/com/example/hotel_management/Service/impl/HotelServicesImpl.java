package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Repository.HotelRepository;
import com.example.hotel_management.Service.HotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Comparator;

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
     * A list of Hotel objects
     */
    @Override
    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    /**
     * Implement method find by owner
     * @param ownerUsername: String
     * @return
     * A list of hotels
     */
    @Override
    public List<Hotel> findByOwnerUsername(String ownerUsername) {
        return this.hotelRepository.findByOwnerUsername(ownerUsername);
    }

    /**
     * Implement find by hotel ID
     * @param hotelID: String
     * @return
     * A list of hotel objects
     */
    @Override
    public List<Hotel> findByHotelID(String hotelID) {
        return this.hotelRepository.findByHotelID(hotelID);
    }

    @Override
    public List<Hotel> findByIsActive(int isActive){
        return this.hotelRepository.findByIsActive(isActive);
    }
}
