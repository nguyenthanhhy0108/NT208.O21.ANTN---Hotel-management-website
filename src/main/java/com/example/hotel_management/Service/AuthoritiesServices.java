package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Authorities;

import java.util.List;
//Define services for Controller layer action easily
public interface AuthoritiesServices {

    /**
     * This service is used to get a list authorities with provided username
     * @param Username: The Username
     * @return
     * List Authorities which contains full role of this user
     */
    List<Authorities> findByUsername(String Username);

    /**
     * This service is used to save an authorities object to database
     * @param authorities: The authorities
     * @return
     * The authorities which is saved
     */
    Authorities save(Authorities authorities);
}
