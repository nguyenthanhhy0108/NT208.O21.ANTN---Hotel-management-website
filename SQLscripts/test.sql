select * from HOTEL

select * from HOTEL_DETAIL

select * from ROOM


SELECT * FROM HOTEL_CAPACITY_BOOKED

insert into HOTEL(hotel_id, owner_username, is_active) values ('1', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL(hotel_id, owner_username, is_active) values ('2', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL(hotel_id, owner_username, is_active) values ('3', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL(hotel_id, owner_username, is_active) values ('4', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL(hotel_id, owner_username, is_active) values ('5', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL(hotel_id, owner_username, is_active) values ('6', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL(hotel_id, owner_username, is_active) values ('7', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL(hotel_id, owner_username, is_active) values ('8', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL(hotel_id, owner_username, is_active) values ('9', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL(hotel_id, owner_username, is_active) values ('10', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL(hotel_id, owner_username, is_active) values ('11', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL(hotel_id, owner_username, is_active) values ('12', '22520593@gm.uit.edu.vn', 0)

insert into HOTEL_DETAIL(hotel_id ,intro, name, country, province, city, street, house_number, phone_number, area, booking_count, total_capacity, price_per_person)
values ('1', 'Hotel from Korean', 'WJH', 'South Korea', 'Seoul', 'Seoul', 'Jeju', '999A', '0941609091', 182004, 999, 0, 0)

INSERT INTO HOTEL_DETAIL(hotel_id ,intro, name, country, province, city, street, house_number, phone_number, area, booking_count, total_capacity, price_per_person)
VALUES
    ('2', 'Hotel from Japan', 'XYZ Hotel', 'Japan', 'Tokyo', 'Tokyo', 'Fuzy', '123B', '0987654321', 200, 500, 0, 0),
    ('3', 'Hotel from Italy', 'ABC Resort', 'Italy', 'Rome', 'Rome', 'Lazio', '456C', '0123456789', 150, 400, 0, 0),
    ('4', 'Hotel from France', 'DEF Inn', 'France', 'Paris', 'Paris', 'Monte', '789D', '0112233445', 180, 450, 0, 0),
    ('5', 'Hotel from Spain', 'GHI Lodge', 'Spain', 'Madrid', 'Madrid', 'Madrid-03', '101E', '0334455667', 170, 550, 0, 0),
    ('6', 'Hotel from Australia', 'JKL Hotel', 'Australia', 'Sydney', 'Sydney', 'Sydney-06', '111F', '0556677889', 220, 600, 0, 0);

INSERT INTO HOTEL_DETAIL(hotel_id ,intro, name, country, province, city, street, house_number, phone_number, area, booking_count, total_capacity, price_per_person)
VALUES
    ('7', 'Introduction for Hotel 7', 'Hotel 7', 'Seoul', 'Gangnam', 'Gangnam-gu', 'Apgujeong-ro', '123', '0123456789', 123456, 123, 456, 789),
    ('8', 'Introduction for Hotel 8', 'Hotel 8', 'Seoul', 'Gangbuk', 'Gangbuk-gu', 'Dobong-ro', '456', '0234567890', 123456, 123, 456, 789),
    ('9', 'Introduction for Hotel 9', 'Hotel 9', 'Seoul', 'Jongno', 'Jongno-gu', 'Jongno', '789', '0345678901', 123456, 123, 456, 789),
    ('10', 'Introduction for Hotel 10', 'Hotel 10', 'Seoul', 'Mapo', 'Mapo-gu', 'Mapo-daero', '101', '0456789012', 123456, 123, 456, 789),
    ('11', 'Introduction for Hotel 11', 'Hotel 11', 'Seoul', 'Yongsan', 'Yongsan-gu', 'Yongsan', '111', '0567890123', 123456, 123, 456, 789),
    ('12', 'Introduction for Hotel 12', 'Hotel 12', 'Seoul', 'Seocho', 'Seocho-gu', 'Seocho-daero', '121', '0678901234', 123456, 123, 456, 789);


INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('1', '1.1', 3, 600, 5)
INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
('1', '1.2', 5, 500, 4.9)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('2', '2.1', 4, 700, 4.8)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('2', '2.2', 6, 900, 5)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('3', '3.1', 2, 400, 4.5)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('3', '3.2', 3, 550, 4.7)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('4', '4.1', 2, 450, 4.6)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('4', '4.2', 4, 650, 4.9)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('5', '5.1', 3, 500, 4.7)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('5', '5.2', 5, 750, 4.8)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('6', '6.1', 4, 600, 4.9)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('6', '6.2', 6, 850, 5)


INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('7', '7.1', 3, 600, 5)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('7', '7.2', 5, 500, 4.9)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('8', '8.1', 3, 650, 4.8)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('8', '8.2', 4, 700, 4.7)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('9', '9.1', 4, 700, 4.6)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('9', '9.2', 6, 800, 4.5)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('10', '10.1', 2, 500, 4.7)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('10', '10.2', 3, 550, 4.8)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('11', '11.1', 5, 750, 4.9)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('11', '11.2', 6, 800, 5)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('12', '12.1', 3, 600, 4.8)

INSERT INTO ROOM(hotel_id, room_id, num_people, price, star_rating) VALUES
    ('12', '12.2', 4, 650, 4.9)

UPDATE HOTEL
SET is_active = 1