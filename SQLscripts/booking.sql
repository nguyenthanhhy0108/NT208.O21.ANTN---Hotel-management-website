use NT;

CREATE TABLE BOOKING (
 booking_id INT PRIMARY KEY IDENTITY(1,1),
 check_in_date DATE,
 check_out_date DATE,
 customer VARCHAR(50),
 hotel_id VARCHAR(50),
 room_id VARCHAR(50),
 total_price FLOAT,
 is_accepted INT DEFAULT 0,

 FOREIGN KEY (customer) REFERENCES USERS(username),
 FOREIGN KEY (hotel_id) REFERENCES HOTEL(hotel_id),
 FOREIGN KEY (room_id) REFERENCES ROOM(room_id)
);