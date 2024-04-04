package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.UserDetails;
import com.example.hotel_management.Repository.UserDetailsRepository;
import com.example.hotel_management.Service.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserDetailsServicesImpl implements UserDetailsServices {
    private final UserDetailsRepository UserDetailsRepository;
    @Autowired
    public UserDetailsServicesImpl(UserDetailsRepository userDetailsRepository) {
        UserDetailsRepository = userDetailsRepository;
    }

    @Override
    public List<UserDetails> findByUsername(String Username) {
        return UserDetailsRepository.findByUsername(Username);
    }
}
