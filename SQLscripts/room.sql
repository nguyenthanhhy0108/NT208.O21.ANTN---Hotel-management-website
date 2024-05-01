use NT;

CREATE TABLE ROOM (
  hotel_id VARCHAR(50),
  room_id VARCHAR(50) PRIMARY KEY,
  num_people INT,
  booked_guests INT DEFAULT 0,
  price FLOAT,
  star_rating FLOAT,
  FOREIGN KEY (hotel_id) references HOTEL(hotel_id)
);