package com.example.hotel_management.Service;

import com.example.hotel_management.Model.UserDetails;

import java.util.List;
//Define services for Controller layer action easily
public interface UserDetailsServices {
    /*
    Input: String Username
    Output: List UserDetails which contains full details of this user
     */
    List<UserDetails> findByUsername(String Username);
    /*
    Input: UserDetails object which you want to save
    Output: This object after saving process
     */
    UserDetails save(UserDetails userDetails);
}
