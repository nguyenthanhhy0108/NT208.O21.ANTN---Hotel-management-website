package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.HotelDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelDetailsRepository extends JpaRepository<HotelDetails,String> {

    /**
     * Find hotel details in database which consists a similar name
     * @param name: String
     * @return
     * A list of HotelDetails objects
     */
    List<HotelDetails> findByName(String name);

    /**
     * Find hotel details in database which consists a similar country
     * @param country: String
     * @return
     * A list of HotelDetails objects
     */
    List<HotelDetails> findByCountry(String country);

    /**
     * Find hotel details in database which consists a similar id
     * @param hotelID: String
     * @return
     * A list of HotelDetails objects
     */
    List<HotelDetails> findByHotelID(String hotelID);

}
