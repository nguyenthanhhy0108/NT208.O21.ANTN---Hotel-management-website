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

    /**
     * Dependency Injection
     * @param userDetailsRepository: UserDetailsRepository object
     */
    @Autowired
    public UserDetailsServicesImpl(UserDetailsRepository userDetailsRepository) {
        UserDetailsRepository = userDetailsRepository;
    }

    /**
     * Implement UserDetailsServices Interface
     * @param Username: The username
     * @return
     * An UserDetails list
     */
    @Override
    public List<UserDetails> findByUsername(String Username) {
        /*
        Call Repository layer and return  an UserDetails list
         */
        return UserDetailsRepository.findByUsername(Username);
    }

    /**
     * Implement UserDetailsServices Interface
     * @param userDetails: UserDetails object
     * @return
     * UserDetails object which is saved
     */
    @Override
    public UserDetails save(UserDetails userDetails) {
        /*
        Call Repository layer and return  an UserDetails object
         */
        return UserDetailsRepository.save(userDetails);
    }
}
