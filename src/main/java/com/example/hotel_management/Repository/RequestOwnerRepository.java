package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Model.RequestOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestOwnerRepository extends JpaRepository<RequestOwner, Integer> {

    /**
     * Find request owner by id
     * @param id: int
     * @return A list of RequestOwner objects with given id
     */
    List<RequestOwner> findByRequestId(int id);

    /**
     * Find all request owner by username
     * @param username: String
     * @return A list of RequestOwner objects with given username
     */
    List<RequestOwner> findByUsername(String username);
}
