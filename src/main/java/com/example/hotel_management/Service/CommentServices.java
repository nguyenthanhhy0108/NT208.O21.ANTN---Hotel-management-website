package com.example.hotel_management.Service;

import com.example.hotel_management.Model.Comment;
import com.example.hotel_management.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CommentServices {
    Comment findByID(String commentID);
    Comment saveAndUpdateBooking(Comment comment, String bookingID);
    List<Comment> findByRoomID(String roomID);
}
