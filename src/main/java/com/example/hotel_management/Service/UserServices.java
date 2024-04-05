package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Users;

import java.util.List;

public interface UserServices {
    List<Users> findByUsername(String Username);
    Users save(Users Users);
    boolean comparePasswordByUsername(String username, String rawPassword);
    void updatePasswordByUsername(String username, String newPassword);
}
