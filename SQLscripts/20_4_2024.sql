ALTER TABLE HOTEL_DETAIL
ADD booking_count bigint;

ALTER TABLE HOTEL_DETAIL
ADD total_capacity bigint;

ALTER TABLE ROOM
ADD CONSTRAINT FK_Hotel_ID
FOREIGN KEY (hotel_id) REFERENCES HOTEL(hotel_id);


create table HOTEL_CAPACITY_BOOKED(
                                      hotel_id varchar(50) not null references HOTEL(hotel_id),
                                      day1 bigint,
                                      day2 bigint,
                                      day3 bigint,
                                      day4 bigint,
                                      day5 bigint,
                                      day6 bigint,
                                      day7 bigint,
                                      day8 bigint,
                                      day9 bigint,
                                      day10 bigint,
                                      day11 bigint,
                                      day12 bigint,
                                      day13 bigint,
                                      day14 bigint,
                                      day15 bigint,
                                      day16 bigint,
                                      day17 bigint,
                                      day18 bigint,
                                      day19 bigint,
                                      day20 bigint,
                                      day21 bigint,
                                      day22 bigint,
                                      day23 bigint,
                                      day24 bigint,
                                      day25 bigint,
                                      day26 bigint,
                                      day27 bigint,
                                      day28 bigint,
                                      day29 bigint,
                                      day30 bigint
)


ALTER TABLE ROOM
DROP CONSTRAINT DF__ROOM__is_booked__571DF1D5; --change that

alter table ROOM
drop column is_booked

alter table HOTEL_DETAIL
add price_per_person money

--TRIGGER ADD ROOM
CREATE TRIGGER after_room_insert
    ON room
    AFTER INSERT
AS
BEGIN
    DECLARE @hotel_capacity INT;
    DECLARE @new_capacity INT;
    DECLARE @new_price BIGINT;

SELECT @hotel_capacity = total_capacity
FROM hotel_detail
WHERE hotel_id = (SELECT hotel_id FROM inserted);

SET @new_capacity = @hotel_capacity + (SELECT num_people FROM inserted);
SET @new_price = (SELECT MIN(price / num_people) FROM ROOM WHERE hotel_id = (SELECT hotel_id FROM inserted));

UPDATE hotel_detail
SET total_capacity = @new_capacity,
    price_per_person = @new_price
WHERE hotel_id = (SELECT hotel_id FROM inserted);
END;


alter table ROOM
drop constraint DF__ROOM__booked_gue__5812160E -- change that if you need


alter table ROOM
drop column booked_guests



--TRIGGER DELETE ROOM
CREATE TRIGGER after_room_deleted
    ON room
    AFTER DELETE
AS
BEGIN
    DECLARE @hotel_capacity INT;
    DECLARE @new_capacity INT;
    DECLARE @new_price BIGINT;

SELECT @hotel_capacity = total_capacity
FROM hotel_detail
WHERE hotel_id = (SELECT hotel_id FROM deleted);

SET @new_capacity = @hotel_capacity - (SELECT num_people FROM deleted);
SET @new_price = (SELECT MIN(price / num_people) FROM ROOM WHERE hotel_id = (SELECT hotel_id FROM deleted));

UPDATE hotel_detail
SET total_capacity = @new_capacity,
    price_per_person = @new_price
WHERE hotel_id = (SELECT hotel_id FROM deleted);
END;

ALTER TABLE HOTEL_CAPACITY_BOOKED
ADD CONSTRAINT p_key PRIMARY KEY (hotel_id);
