package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Repository.HotelDetailsRepository;
import com.example.hotel_management.Service.HotelDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    /**
     * Implement find all hotel ID
     * @return
     * A list of hotel ID
     */
    @Override
    public List<String> findAllHotelID() {
        List<HotelDetails> allHotelDetails = this.hotelDetailsRepository.findAll();
        List<String> hotelID = new ArrayList<>();
        for(HotelDetails hotelDetails : allHotelDetails){
            hotelID.add(hotelDetails.getHotelID());
        }
        return hotelID;
    }

    /**
     * Implement find by hotel name
     * @return
     * A list of HotelDetails objects
     */
    @Override
    public List<HotelDetails> findByName(String hotelName) {
        return this.hotelDetailsRepository.findByName(hotelName);
    }

    /**
     * Implement get hotel name sorted by booking count in a specific country
     * @param country: String
     * @param option: int
     * @return
     * A list of objects
     */
    @Override
    public List<Object> getHotelNameSortedList(String country, int option) {

        List<HotelDetails> allHotelDetails = this.hotelDetailsRepository.findByCountry(country);

        if(option == 1){
            allHotelDetails.sort(Comparator.comparing(HotelDetails::getBookingCount).reversed());
        }
        if(option == 2){
            allHotelDetails.sort(Comparator.comparing(HotelDetails::getBookingCount));
        }
        if(option == 3){
            allHotelDetails.sort(Comparator.comparing(HotelDetails::getPricePerPerson).reversed());
        }
        if(option == 4){
            allHotelDetails.sort(Comparator.comparing(HotelDetails::getPricePerPerson));
        }

        List<Object> result = new ArrayList<>();

        for(HotelDetails hotelDetails : allHotelDetails){
            result.add(hotelDetails.getName());
        }

        return result;
    }

    /**
     * Implement method get list hotel address by provided list name
     * @param providedListName: List<String>
     * @return
     * A list object
     */
    @Override
    public List<Object> getListHotelAddressByProvidedListName(List<String> providedListName) {
        List<Object> result = new ArrayList<>();
        for(String name : providedListName){
            String address = this.hotelDetailsRepository
                    .findByName(name)
                    .get(0)
                    .getAddress();
            result.add(address);
        }
        return result;
    }

    /**
     * Implement method get list price for a specific group by provided list name
     * @param providedListName: List<String>
     * @param numberOfPeople: int
     * @return
     * A list object
     */
    @Override
    public List<Object> getListPriceForASpecificGroupByProvidedListName(List<String> providedListName,
                                                                        int numberOfPeople) {
        List<Object> result = new ArrayList<>();
        for(String name : providedListName){
            long price = this.hotelDetailsRepository
                    .findByName(name)
                    .get(0)
                    .getPricePerPerson() * numberOfPeople;
            result.add(price);
        }
        return result;
    }

    /**
     * Implement get a list id by provided list name
     * @param providedListName: List<String>
     * @return
     * A list id
     */
    @Override
    public List<Object> getListIDByProvidedListName(List<String> providedListName) {
        List<Object> result = new ArrayList<>();
        for(String name : providedListName){
            String id = this.hotelDetailsRepository
                    .findByName(name)
                    .get(0)
                    .getHotelID();
            result.add(id);
        }
        return result;
    }

    /**
     * Implement find by hotel ID
     * @param hotelID: String
     * @return
     * A list of hotel details
     */
    @Override
    public List<HotelDetails> findByHotelID(String hotelID) {
        return this.hotelDetailsRepository.findByHotelID(hotelID);
    }
}
