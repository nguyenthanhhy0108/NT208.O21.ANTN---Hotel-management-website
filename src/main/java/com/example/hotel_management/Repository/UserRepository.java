package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//This interface is used for interact with USERS table in database
@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    /*
    Input: String Username
    Output: List Users
     */
    List<Users> findByUsername(String Username);
}
