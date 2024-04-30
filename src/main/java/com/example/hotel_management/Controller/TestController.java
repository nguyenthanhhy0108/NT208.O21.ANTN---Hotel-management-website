package com.example.hotel_management.Controller;

import com.example.hotel_management.Model.Hotel;
import com.example.hotel_management.Model.HotelDetails;
import com.example.hotel_management.Service.HotelDetailsServices;
import com.example.hotel_management.Service.HotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    private final HotelServices hotelServices;
    private final HotelDetailsServices hotelDetailsServices;

    @Autowired
    public TestController(HotelServices hotelServices, HotelDetailsServices hotelDetailsServices) {
        this.hotelServices = hotelServices;
        this.hotelDetailsServices = hotelDetailsServices;
    }

    @GetMapping("/test")
    public String test() {
//        Hotel hotel1 = hotelServices.saveHotel(new Hotel("22520593@gm.uit.edu.vn"));
//        Hotel hotel2 = hotelServices.saveHotel(new Hotel("22520593@gm.uit.edu.vn"));
//        Hotel hotel3 = hotelServices.saveHotel(new Hotel("22520593@gm.uit.edu.vn"));
//        System.out.println(Hotel.countHotel);
//        System.out.println(hotelDetailsServices.getHotelSortedByBookingCount().get(0).getBookingCount());
        return "test";
    }
//    public ResponseEntity<Map<String, Object>> test(){
//        Map<String, Object> map = new HashMap<>();
//
////        hotelServices.saveHotel(new Hotel("123", "22520593@gm.uit.edu.vn"));
////        hotelDetailsServices.save(new HotelDetails("123",
////                "abc",
////                "abc",
////                "abc",
////                "abc",
////                "abc",
////                "abc",
////                "abc",
////                "abc",
////                (float) 1.5));
//        hotelServices.deleteByHotelId("123");
//
//        map.put("Done", true);
//        return ResponseEntity.ok(map);
//    }
}
