package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.BookedCapacity;
import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Repository.BookedCapacityRepository;
import com.example.hotel_management.Service.BookedCapacityServices;
import com.example.hotel_management.Service.HotelDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookedCapacityServicesImpl implements BookedCapacityServices {

    private final BookedCapacityRepository bookedCapacityRepository;
    private final HotelDetailsServices hotelDetailsServices;

    /**
     * Dependency Injection
     * @param bookedCapacityRepository: BookedCapacityRepository object
     */
    @Autowired
    public BookedCapacityServicesImpl(BookedCapacityRepository bookedCapacityRepository,
                                      HotelDetailsServices hotelDetailsServices) {
        this.bookedCapacityRepository = bookedCapacityRepository;
        this.hotelDetailsServices = hotelDetailsServices;
    }

    /**
     * Implement find by hotel ID
     * @param hotelId: String
     * @return
     * A BookedCapacity object
     */
    @Override
    public BookedCapacity findByHotelID(String hotelId) {
        return this.bookedCapacityRepository.findByHotelID(hotelId);
    }

    /**
     * Implement get satisfied hotel names
     * @param checkInIndex: int
     * @param checkOutIndex: int
     * @param numberOfPeople: int
     * @param country: String
     * @param option: int
     * @return
     * A names list
     */
    @Override
    public List<Object> getSatisfiedHotelNames(int checkInIndex,
                                               int checkOutIndex,
                                               int numberOfPeople,
                                               String country,
                                               int option) {

        List<Object> allHotelDetailsName = hotelDetailsServices.getHotelNameSortedList(country, option);


        List<Object> toRemove = new ArrayList<>();
        for(Object name: allHotelDetailsName){
            String hotelName = name.toString();

            HotelDetails details = this.hotelDetailsServices.findByName(hotelName).get(0);

            boolean flag = true;
            BookedCapacity schedule = this.bookedCapacityRepository.findByHotelID(details.getHotelID());
            for(long i = checkInIndex; i <= checkOutIndex; i++){
                int index = (int)i;
                long bookedCapacity = Long.parseLong(schedule.toList().get(index).toString());
                long currentCapacity = details.getTotalCapacity() - bookedCapacity;

                if(currentCapacity < numberOfPeople){
                    flag = false;
                }
            }
            if(!flag){
                toRemove.add(name);
            }
        }
        allHotelDetailsName.removeAll(toRemove);
        return allHotelDetailsName;
    }
}
