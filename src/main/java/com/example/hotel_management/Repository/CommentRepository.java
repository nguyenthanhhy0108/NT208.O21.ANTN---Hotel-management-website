package com.example.hotel_management.Repository;

import com.example.hotel_management.Model.Comment;
import com.example.hotel_management.Model.HotelDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String>{
    List<Comment> findByRoomID(String roomID);
}
