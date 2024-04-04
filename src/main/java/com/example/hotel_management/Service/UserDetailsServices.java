package com.example.hotel_management.Service;

import com.example.hotel_management.Model.UserDetails;

import java.util.List;

public interface UserDetailsServices {
    List<UserDetails> findByUsername(String Username);
}
