package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    List<Users> findByUsername(String Username);
}
