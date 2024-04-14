package com.example.hotel_management.Service;

import com.example.hotel_management.Model.UserDetails;

import java.util.List;
//Define services for Controller layer action easily
public interface UserDetailsServices {

    /**
     * This Service is used to find a list of UserDetails object in database
     * @param Username: The username which you want to find UserDetails object
     * @return
     * A list UserDetails which contains full details of this user
     */
    List<UserDetails> findByUsername(String Username);
    /*
    Input: UserDetails object which you want to save
    Output: This object after saving process
     */

    /**
     * This Service is used to save an UserDetails object to database
     * @param userDetails: UserDetails object which you want to save
     * @return
     * This object after saving process
     */
    UserDetails save(UserDetails userDetails);
}
