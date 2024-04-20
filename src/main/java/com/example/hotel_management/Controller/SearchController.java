package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Service.HotelDetailsServices;
import com.example.hotel_management.Service.HotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    private final HotelServices hotelServices;
    private final HotelDetailsServices hotelDetailsServices;

    @Autowired
    public SearchController(HotelServices hotelServices,
                            HotelDetailsServices hotelDetailsServices) {
        this.hotelServices = hotelServices;
        this.hotelDetailsServices = hotelDetailsServices;
    }

    @GetMapping("/suggest")
    public List<String> suggestNames() {
        return hotelDetailsServices.findAllHotelName();
    }
}