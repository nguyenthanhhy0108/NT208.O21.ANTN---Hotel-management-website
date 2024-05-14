package com.example.hotel_management.Model.DataDTO;

import com.example.hotel_management.Model.Room;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class HotelImageDTO{
    private String hotelId;
    private List<String> imageURL;
    private Boolean isOwner;

    public HotelImageDTO(String hotelId, List<String> imageURL){
        this.hotelId = hotelId;
        this.imageURL = imageURL;
    }

    public HotelImageDTO(){
        this.imageURL = new ArrayList<>();
        this.isOwner = false;
    }
}
