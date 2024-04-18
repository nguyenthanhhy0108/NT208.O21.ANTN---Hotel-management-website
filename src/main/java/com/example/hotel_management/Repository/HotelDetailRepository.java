package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Hotel_detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelDetailRepository extends JpaRepository<Hotel_detail,String> {

}
