use NT;

-- Create 'hotel' table
CREATE TABLE HOTEL (
    hotel_id VARCHAR(50) PRIMARY KEY,
    owner_username VARCHAR(50),
    FOREIGN KEY (owner_username) REFERENCES USERS(username)
);

-- Create 'hotel_detail' table
CREATE TABLE HOTEL_DETAIL (
    hotel_id VARCHAR(50) PRIMARY KEY,
    FOREIGN KEY (hotel_id) REFERENCES HOTEL(hotel_id),
    intro VARCHAR(255),
    name VARCHAR(50),
    country VARCHAR(50),
    province VARCHAR(50),
    city VARCHAR(50),
    street VARCHAR(50),
    house_number VARCHAR(50),
    phone_number VARCHAR(20),
    area FLOAT
);