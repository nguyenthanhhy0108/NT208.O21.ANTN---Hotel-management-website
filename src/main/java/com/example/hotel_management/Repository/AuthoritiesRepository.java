package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Authorities;
import com.example.hotel_management.Model.ID.AuthoritiesID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//This interface is used for interact with AUTHORITIES table in database
@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthoritiesID> {

    /**
     * Find a list authority of a user
     * @param Username: The username
     * @return
     * List Authorities which contains full role of this user
     */
    List<Authorities> findByUsername(String Username);
}
