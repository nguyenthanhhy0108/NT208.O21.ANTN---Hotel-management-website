package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "USERDETAILS")
public class UserDetails {
    @Id
    @Column(name = "Username")
    private String username;
    @Column(name = "Nationality")
    private String nationality;

    @OneToOne
    @JoinColumn(name = "Username", insertable = false, updatable = false)
    private Users users;

    public UserDetails() {
    }

    public UserDetails(String username, String nationality) {
        username = username;
        nationality = nationality;
    }
}
