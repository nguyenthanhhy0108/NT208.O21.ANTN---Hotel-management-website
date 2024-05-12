create table ROOM_IMAGE(
                              room_id VARCHAR(50),
                              record_id INT IDENTITY(1,1),
                              URL varchar(200),
                              Primary key (record_id),
                              FOREIGN KEY (room_id) references ROOM(room_id)
);

create table HOTEL_IMAGE(
                           hotel_id VARCHAR(50),
                           record_id INT IDENTITY(1,1),
                           URL varchar(200),
                           Primary key (record_id),
                           FOREIGN KEY (hotel_id) references HOTEL(hotel_id)
);