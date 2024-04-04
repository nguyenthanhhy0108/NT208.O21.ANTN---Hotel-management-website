package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {
    List<UserDetails> findByUsername(String Username);
}
