package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Repository.HotelDetailsRepository;
import com.example.hotel_management.Service.HotelDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class HotelDetailsServicesImpl implements HotelDetailsServices {

    private final HotelDetailsRepository hotelDetailsRepository;

    /**
     * Dependency Injection
     * @param hotelDetailsRepository: HotelDetailsRepository object
     */
    @Autowired
    public HotelDetailsServicesImpl(HotelDetailsRepository hotelDetailsRepository) {
        this.hotelDetailsRepository = hotelDetailsRepository;
    }

    /**
     * Implement find by ID
     * @param id id of hotel detail (String)
     * @return
     * HotelDetails object
     */
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

    /**
     * Implement findAll
     * @return
     * A list of HotelDetails object
     */
    @Override
    public List<HotelDetails> findAll(){
        return hotelDetailsRepository.findAll();
    }

    /**
     * Implement save
     * @param hotelDetails (Hotel detail)
     * @return
     * HotelDetails object which was saved
     */
    @Override
    public HotelDetails save(HotelDetails hotelDetails) {
        return hotelDetailsRepository.save(hotelDetails);
    }

    /**
     * Implement delete
     * @param id id of hotel detail (String)
     */
    @Override
    public void delete(String id) {
        hotelDetailsRepository.deleteById(id);
    }

    /**
     * Implement find all hotel name
     * @return
     * A list of hotel name
     */
    @Override
    public List<String> findAllHotelName() {
        List<HotelDetails> allHotelDetails = this.hotelDetailsRepository.findAll();
        List<String> hotelName = new ArrayList<>();
        for(HotelDetails hotelDetails : allHotelDetails){
            hotelName.add(hotelDetails.getName());
        }
        return hotelName;
    }
}
