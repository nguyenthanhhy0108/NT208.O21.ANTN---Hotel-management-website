package com.example.hotel_management.Model.DataDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDTO {
    private String roomID;
    private int numOfPeople;
    private float price;
}
