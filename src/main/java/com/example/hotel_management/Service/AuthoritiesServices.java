package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Authorities;

import java.util.List;
//Define services for Controller layer action easily
public interface AuthoritiesServices {
    /*
    Input: String Username
    Output: List Authorities which contains full role of this user
     */
    List<Authorities> findByUsername(String Username);
    /*
    Input: A new Authorities object
    Output: A Authorities object which saved in database
     */
    Authorities save(Authorities authorities);
}
