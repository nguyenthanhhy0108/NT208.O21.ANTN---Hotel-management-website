package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Authorities;
import com.example.hotel_management.Model.ID.AuthoritiesID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthoritiesID> {
    List<Authorities> findByUsername(String Username);
}
