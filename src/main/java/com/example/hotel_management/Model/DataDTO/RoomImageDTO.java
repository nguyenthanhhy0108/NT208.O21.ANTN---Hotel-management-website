package com.example.hotel_management.Model.DataDTO;

import com.example.hotel_management.Model.Room;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class RoomImageDTO{
    private String roomId;
    private List<String> imageURL;
    private Boolean isOwner;

    public RoomImageDTO(String roomId, List<String> imageURL){
        this.roomId = roomId;
        this.imageURL = imageURL;
    }

    public RoomImageDTO(){
        this.imageURL = new ArrayList<>();
        this.isOwner = false;
    }
}
