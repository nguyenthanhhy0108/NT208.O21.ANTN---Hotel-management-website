package com.example.hotel_management.Model.DataDTO;

import com.example.hotel_management.Model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class CommentDTO {
    private List<Comment> commentList;

    public CommentDTO(){
        commentList = new ArrayList<>();
    }
}
