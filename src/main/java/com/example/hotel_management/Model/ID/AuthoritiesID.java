package com.example.hotel_management.Model.ID;

import lombok.Data;

import java.io.Serializable;
//Define a class which is ID for Authorities table
@Data
public class AuthoritiesID implements Serializable {
    private String username;
    private String authority;

    public AuthoritiesID() {
    }

    public AuthoritiesID(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
}