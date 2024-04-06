package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.UserDetails;
import com.example.hotel_management.Repository.UserDetailsRepository;
import com.example.hotel_management.Service.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//Implement defined interface
@Service
public class UserDetailsServicesImpl implements UserDetailsServices {
    //Define some attribute
    private final UserDetailsRepository UserDetailsRepository;
    //Dependency Injection
    @Autowired
    public UserDetailsServicesImpl(UserDetailsRepository userDetailsRepository) {
        UserDetailsRepository = userDetailsRepository;
    }
    //Call Repository layer and return  an UserDetails list
    @Override
    public List<UserDetails> findByUsername(String Username) {
        return UserDetailsRepository.findByUsername(Username);
    }
    //Call Repository layer and return  an UserDetails object
    @Override
    public UserDetails save(UserDetails userDetails) {
        return UserDetailsRepository.save(userDetails);
    }
}
