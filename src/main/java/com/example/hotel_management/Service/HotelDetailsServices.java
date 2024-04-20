package com.example.hotel_management.Service;

import com.example.hotel_management.Model.HotelDetails;

import java.util.List;

public interface HotelDetailsServices {
    /**
     * Find all hotel detail
     * @return List Hotel detail
     */
    List<HotelDetails> findAll();

    /**
     * Find hotel detail by id
     * @param id id of hotel detail (String)
     * @return Hotel detail
     */
    HotelDetails findById(String id);

    /**
     * Save the chosen hotel detail
     * @param hotel_detail (Hotel detail)
     * @return the saved hotel detail
     */
    HotelDetails save(HotelDetails hotel_detail);

    /**
     * Delete the chosen hotel detail by id
     * @param id id of hotel detail (String)
     */
    void delete(String id);

    /**
     * Find all hotel name in database
     * @return
     * A list of hotel name
     */
    List<String> findAllHotelName();
}
