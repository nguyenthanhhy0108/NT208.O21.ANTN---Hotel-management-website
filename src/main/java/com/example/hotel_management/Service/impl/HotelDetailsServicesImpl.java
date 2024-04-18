package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Repository.HotelDetailsRepository;
import com.example.hotel_management.Service.HotelDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HotelDetailsServicesImpl implements HotelDetailsServices {

    private final HotelDetailsRepository hotelDetailsRepository;

    @Autowired
    public HotelDetailsServicesImpl(HotelDetailsRepository hotelDetailsRepository) {
        this.hotelDetailsRepository = hotelDetailsRepository;
    }

    @Override
    public HotelDetails findById(String id){
        Optional<HotelDetails> result =  hotelDetailsRepository.findById(id);

        HotelDetails hotelDetail = null;
        if(result.isPresent()){
            hotelDetail = result.get();
        }
        else {
            throw new RuntimeException("Did not find hotel id - " + id);
        }
        return hotelDetail;
    }

    @Override
    public List<HotelDetails> findAll(){
        return hotelDetailsRepository.findAll();
    }

    @Override
    public HotelDetails save(HotelDetails hotelDetails) {
        return hotelDetailsRepository.save(hotelDetails);
    }

    @Override
    public void delete(String id) {
        hotelDetailsRepository.deleteById(id);
    }
}
