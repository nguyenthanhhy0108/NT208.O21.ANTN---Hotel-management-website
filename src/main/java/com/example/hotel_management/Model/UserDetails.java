package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;
//This class is match with USERDETAILS table in database
//Join column Username, OneToOne with USERS table
@Data
@Entity
@Table(name = "USERDETAILS")
public class UserDetails {
    @Id
    @Column(name = "Username")
    private String username;
    @Column(name = "Name")
    private String name;
    @Column(name = "Nationality")
    private String nationality;
    @Column(name = "Address")
    private String address;
    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "Username", insertable = false, updatable = false)
    private Users users;

    public UserDetails() {
    }

    public UserDetails(String username, String name, String nationality, String address, String phoneNumber) {
        this.username = username;
        this.name = name;
        this.nationality = nationality;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
