package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Users;
import com.example.hotel_management.Repository.AuthoritiesRepository;
import com.example.hotel_management.Repository.UsersRepository;
import com.example.hotel_management.Service.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsersServicesImpl implements UsersServices {
    private final UsersRepository UsersRepository;
    private final AuthoritiesRepository AuthoritiesRepository;
    @Autowired
    public UsersServicesImpl(UsersRepository usersRepository, AuthoritiesRepository authoritiesRepository) {
        UsersRepository = usersRepository;
        AuthoritiesRepository = authoritiesRepository;
    }

    @Override
    public List<Users> findByUsername(String Username) {
        return UsersRepository.findByUsername(Username);
    }
}
