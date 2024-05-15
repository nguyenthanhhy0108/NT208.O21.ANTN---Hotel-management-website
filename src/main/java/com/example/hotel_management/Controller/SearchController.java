package com.example.hotel_management.Controller;

import com.example.hotel_management.Service.BookedCapacityServices;
import com.example.hotel_management.Service.HotelDetailsServices;
import com.example.hotel_management.Service.HotelImageRecordServices;
import com.example.hotel_management.Service.HotelServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class SearchController {

    private final HotelDetailsServices hotelDetailsServices;
    private final BookedCapacityServices bookedCapacityServices;
    private final HotelImageRecordServices hotelImageRecordServices;
    private long checkInIndex;
    private long checkOutIndex;
    private int numberOfPeople;

    /**
     * Dependency Injection
     * @param hotelDetailsServices: HotelDetailsServices object
     * @param hotelServices: HotelServices object
     * @param bookedCapacityServices: BookedCapacityServices object
     */
    @Autowired
    public SearchController(HotelDetailsServices hotelDetailsServices,
                            HotelServices hotelServices,
                            BookedCapacityServices bookedCapacityServices,
                            HotelImageRecordServices hotelImageRecordServices) {
        this.hotelDetailsServices = hotelDetailsServices;
        this.bookedCapacityServices = bookedCapacityServices;
        this.hotelImageRecordServices = hotelImageRecordServices;
    }

    /**
     * Suggest for search bar
     * @return
     * Suggested list
     */
    @GetMapping("/suggest")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> suggestNames() {

        HashMap<String, Object> response = new HashMap<>();

        List<String> allName = hotelDetailsServices.findAllHotelName();
        List<String> allID = hotelDetailsServices.findAllHotelID();

        response.put("allName", allName);
        response.put("allID", allID);

        return ResponseEntity.ok(response);
    }

    /**
     * Process when user do search action
     * @param typedName: String
     * @param people: int
     * @param checkInDate: String
     * @param checkOutDate: String
     * @return
     * Redirect to some html
     */
    @GetMapping("/search")
    public String searchHotel(HttpServletRequest request,
                              @RequestParam("name") String typedName,
                              @RequestParam("people") int people,
                              @RequestParam("check_in_date") String checkInDate,
                              @RequestParam("check_out_date") String checkOutDate) {

        HttpSession session = request.getSession();
        session.setAttribute("checkInDate", checkInDate);
        session.setAttribute("checkOutDate", checkOutDate);

        this.numberOfPeople = people;

        checkInDate = checkInDate + "T00:00:00";
        checkOutDate = checkOutDate + "T00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime checkIn = LocalDateTime.parse(checkInDate, formatter);
        LocalDateTime checkOut = LocalDateTime.parse(checkOutDate, formatter);

        LocalDateTime today = LocalDateTime.now();
        checkInIndex = ChronoUnit.DAYS.between(today, checkIn);
        checkOutIndex = ChronoUnit.DAYS.between(today, checkOut);

        List<String> allName = hotelDetailsServices.findAllHotelName();
        if (allName.contains(typedName)) {
            String id = hotelDetailsServices.findByName(typedName).get(0).getHotelID();
            return "redirect:/hotel-detail?hotel_id=" + id;
        }

        List<String> countries = Arrays.asList(
                "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Anguilla", "Antigua & Barbuda", "Argentina",
                "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh",
                "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
                "Bosnia & Herzegovina", "Botswana", "Brazil", "British Virgin Islands", "Brunei", "Bulgaria",
                "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
                "Central African Republic", "Chad", "Chile", "China", "Colombia", "Congo", "Cook Islands",
                "Costa Rica", "Cote D'Ivoire", "Croatia", "Cuba", "Curacao", "Cyprus", "Czech Republic",
                "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador",
                "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands",
                "Fiji", "Finland", "France", "French Polynesia", "French West Indies", "Gabon", "Gambia",
                "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guam",
                "Guatemala", "Guernsey", "Guinea", "Guinea Bissau", "Guyana", "Haiti", "Honduras", "Hong Kong",
                "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel",
                "Italy", "Jamaica", "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo",
                "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
                "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi",
                "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius",
                "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat",
                "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands",
                "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria",
                "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama",
                "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal",
                "Puerto Rico", "Qatar", "Reunion", "Romania", "Russia", "Rwanda", "Saint Pierre & Miquelon",
                "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia",
                "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
                "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka",
                "St Kitts & Nevis", "St Lucia", "St Vincent", "Sudan", "Suriname", "Swaziland",
                "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand",
                "Timor L'Este", "Togo", "Tonga", "Trinidad & Tobago", "Tunisia", "Turkey",
                "Turkmenistan", "Turks & Caicos", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates",
                "United Kingdom", "United States of America", "Uruguay", "Uzbekistan", "Vanuatu",
                "Vatican City", "Venezuela", "Vietnam", "Virgin Islands (US)", "Yemen", "Zambia", "Zimbabwe"
        );

        if(!countries.contains(typedName)){
            return "redirect:/first-page?not_exist=true";
        }

        return "redirect:/home?country=" + typedName + "&page=1" + "&numberOfPeople=" + this.numberOfPeople + "&option=1";
    }

    /**
     * Get search result
     * @param country: String
     * @param numberOfPeople: int
     * @param option: int
     * @return
     * Json for front end
     */
    @GetMapping("/get-sorted-hotels-details")
    @ResponseBody
    public ResponseEntity<Map<String, List<String>>> getHotelDetails(@RequestParam String country,
                                                                     @RequestParam int numberOfPeople,
                                                                     @RequestParam int option) {

        HashMap<String, List<String>> map = new HashMap<>();

        int intCheckInIndex = (int)this.checkInIndex;
        int intCheckOutIndex = (int)this.checkOutIndex;

        List<Object> namesObject = this.bookedCapacityServices.getSatisfiedHotelNames(intCheckInIndex,
                intCheckOutIndex,
                numberOfPeople,
                country,
                option);

        List<String> names = new ArrayList<>();

        for(Object name : namesObject) {
            names.add((String) name);
        }

        List<Object> prices = this.hotelDetailsServices.getListPriceForASpecificGroupByProvidedListName(names,
                numberOfPeople);

        List<String> pricesString = new ArrayList<>();
        for(Object price : prices) {
            pricesString.add(price.toString());
        }

        List<Object> addresses = this.hotelDetailsServices.getListHotelAddressByProvidedListName(names);
        List<String> addressesString = new ArrayList<>();
        for(Object address : addresses) {
            addressesString.add((String) address);
        }

        List<String> numberOfPeopleToFE = new ArrayList<>();
        numberOfPeopleToFE.add(String.valueOf(numberOfPeople));

        List<Object> ids = new ArrayList<>();
        ids = this.hotelDetailsServices.getListIDByProvidedListName(names);
        List<String> idsString = new ArrayList<>();
        for(Object id : ids) {
            idsString.add((String) id);
        }

        List<String> imageURL = new ArrayList<>();

        for(String hotelID : idsString){
            String publicURL = hotelImageRecordServices.findAvatarByHotelID(hotelID);
            imageURL.add(publicURL);
        }


        map.put("names", names);
        map.put("prices", pricesString);
        map.put("addresses", addressesString);
        map.put("numberOfPeople", numberOfPeopleToFE);
        map.put("ids", idsString);
        map.put("images", imageURL);

        return ResponseEntity.ok(map);
    }
}