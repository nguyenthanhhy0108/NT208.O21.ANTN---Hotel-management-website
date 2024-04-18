package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Hotel_detail;
import com.example.hotel_management.Repository.HotelDetailRepository;
import com.example.hotel_management.Service.HotelDetailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HotelDetailServicesImpl implements HotelDetailServices {

    private final HotelDetailRepository hotelDetailRepository;

    @Autowired
    public HotelDetailServicesImpl(HotelDetailRepository hotelDetailRepository) {
        this.hotelDetailRepository = hotelDetailRepository;
    }

    @Override
    public Hotel_detail findById(String id){
        Optional<Hotel_detail> result =  hotelDetailRepository.findById(id);

        Hotel_detail hotel_detail = null;
        if(result.isPresent()){
            hotel_detail = result.get();
        }
        else {
            throw new RuntimeException("Did not find hotel id - " + id);
        }
        return hotel_detail;
    }

    @Override
    public List<Hotel_detail> findAll(){
        return hotelDetailRepository.findAll();
    }

    @Override
    public void save(Hotel_detail hotel_detail) {
        hotelDetailRepository.save(hotel_detail);
    }

    @Override
    public void delete(String id) {
        hotelDetailRepository.deleteById(id);
    }
}
