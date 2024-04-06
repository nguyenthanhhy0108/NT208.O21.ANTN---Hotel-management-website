package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//This interface is used for interact with USERDETAILS table in database
@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {
    /*
    Input: String Username
    Output: List UserDetails which contains full details of this user
     */
    List<UserDetails> findByUsername(String Username);
}
