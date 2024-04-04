package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Authorities;

import java.util.List;

public interface AuthoritiesServices {
    List<Authorities> findByUsername(String Username);
}
