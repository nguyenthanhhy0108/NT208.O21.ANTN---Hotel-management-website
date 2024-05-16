package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Booking;
import com.example.hotel_management.Model.Comment;
import com.example.hotel_management.Model.DataDTO.CommentDTO;
import com.example.hotel_management.Repository.CommentRepository;
import com.example.hotel_management.Service.BookingServices;
import com.example.hotel_management.Service.CommentServices;
import com.example.hotel_management.Service.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServicesImpl implements CommentServices {
    private final CommentRepository commentRepository;
    private final BookingServices bookingServices;
    private final RoomServices roomServices;

    @Autowired
    public CommentServicesImpl(CommentRepository commentRepository, BookingServices bookingServices, RoomServices roomServices){
        this.commentRepository = commentRepository;
        this.bookingServices = bookingServices;
        this.roomServices = roomServices;
    }

    @Override
    public Comment findByID(String commentID){
        Optional<Comment> result = this.commentRepository.findById(commentID);
        if (result.isEmpty()){
            return null;
        }
        else{
            return result.get();
        }
    }

    @Override
    public Comment saveAndUpdateBooking(Comment comment, String bookingID){
        Booking requestBooking = bookingServices.findById(bookingID);

        Comment savedComment = this.commentRepository.save(comment);
        if(savedComment != null){
            requestBooking.setIsRated(1);
            bookingServices.onlySave(requestBooking);
        }
        return savedComment;
    }

    @Override
    public List<Comment> findByRoomID(String roomID){
        return this.commentRepository.findByRoomID(roomID);
    }
}
