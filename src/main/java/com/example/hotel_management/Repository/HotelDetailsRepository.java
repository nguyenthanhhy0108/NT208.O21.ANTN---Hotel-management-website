package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.HotelDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelDetailsRepository extends JpaRepository<HotelDetails,String> {

}
