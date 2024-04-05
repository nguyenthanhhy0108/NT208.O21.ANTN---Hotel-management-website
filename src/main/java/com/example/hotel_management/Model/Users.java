package com.example.hotel_management.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "USERS")
public class Users {
    @Id
    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Enabled")
    private int enabled;

    @OneToOne(mappedBy = "users", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.REMOVE
    })
    private UserDetails UserDetails;

    @OneToMany(mappedBy = "users", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private List<Authorities> Authorities;
    public Users() {
    }

    public Users(String username, String password, int enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
}
