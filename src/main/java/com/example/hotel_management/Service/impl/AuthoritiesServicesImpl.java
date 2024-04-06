package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Authorities;
import com.example.hotel_management.Repository.AuthoritiesRepository;
import com.example.hotel_management.Service.AuthoritiesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//Implement defined interface
@Service
public class AuthoritiesServicesImpl implements AuthoritiesServices {
    //Define some attribute
    private final AuthoritiesRepository AuthoritiesRepository;
    //Dependency Injection
    @Autowired
    public AuthoritiesServicesImpl(AuthoritiesRepository authoritiesRepository) {
        AuthoritiesRepository = authoritiesRepository;
    }

    //Call Repository layer and return  an authorities list
    @Override
    public List<Authorities> findByUsername(String Username) {
        return AuthoritiesRepository.findByUsername(Username);
    }
    //Call Repository layer and return  an authorities object
    @Override
    public Authorities save(Authorities authorities) {
        return AuthoritiesRepository.save(authorities);
    }
}
