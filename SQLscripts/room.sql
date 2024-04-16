use NT;

CREATE TABLE ROOM (
  hotel_id VARCHAR(50),
  room_id VARCHAR(50) PRIMARY KEY,
  num_people INT,

  price FLOAT,
  is_booked INT DEFAULT 0,
  booked_guests INT DEFAULT 0,
  star_rating FLOAT
);