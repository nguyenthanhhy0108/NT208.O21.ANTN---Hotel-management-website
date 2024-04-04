package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Users;

import java.util.List;

public interface UsersServices {
    List<Users> findByUsername(String Username);
}
