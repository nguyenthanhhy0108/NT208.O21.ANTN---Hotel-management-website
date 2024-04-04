package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Authorities;
import com.example.hotel_management.Repository.AuthoritiesRepository;
import com.example.hotel_management.Service.AuthoritiesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthoritiesServicesImpl implements AuthoritiesServices {

    private final AuthoritiesRepository AuthoritiesRepository;
    @Autowired
    public AuthoritiesServicesImpl(AuthoritiesRepository authoritiesRepository) {
        AuthoritiesRepository = authoritiesRepository;
    }

    @Override
    public List<Authorities> findByUsername(String Username) {
        return AuthoritiesRepository.findByUsername(Username);
    }
}
