package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Users;
import com.example.hotel_management.Repository.AuthoritiesRepository;
import com.example.hotel_management.Repository.UserRepository;
import com.example.hotel_management.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository UserRepository;
    private final AuthoritiesRepository AuthoritiesRepository;
    @Autowired
    public UserServicesImpl(UserRepository userRepository, AuthoritiesRepository authoritiesRepository) {
        UserRepository = userRepository;
        AuthoritiesRepository = authoritiesRepository;
    }

    @Override
    public List<Users> findByUsername(String Username) {
        return UserRepository.findByUsername(Username);
    }

    @Override
    public Users save(Users users) {
        return UserRepository.save(users);
    }
}
