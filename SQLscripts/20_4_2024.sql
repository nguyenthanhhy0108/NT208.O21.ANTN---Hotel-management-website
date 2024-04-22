ALTER TABLE HOTEL_DETAIL
ADD booking_count bigint;

update HOTEL_DETAIL
set booking_count = 1;

ALTER TABLE HOTEL_DETAIL
ADD current_capacity bigint;

update HOTEL_DETAIL
set current_capacity = 0;

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
DROP CONSTRAINT DF__ROOM__is_booked__6477ECF3;

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
	DECLARE @total_price BIGINT;
	DECLARE @new_room_price BIGINT;

SELECT @hotel_capacity = total_capacity, @total_price = total_capacity * price_per_person
FROM hotel_detail
WHERE hotel_id = (SELECT hotel_id FROM inserted);

SET @new_capacity = @hotel_capacity + (SELECT num_people FROM inserted);
SET @new_room_price = (SELECT price FROM inserted);

UPDATE hotel_detail
SET total_capacity = @new_capacity,
    price_per_person = (@total_price + @new_room_price) / @new_capacity
WHERE hotel_id = (SELECT hotel_id FROM inserted);

END;


alter table ROOM
drop constraint DF__ROOM__booked_gue__656C112C;


alter table ROOM
drop column booked_guests


--TRIGGER DELETE ROOM
CREATE TRIGGER after_room_delete
    ON room
    AFTER DELETE
AS
BEGIN

    DECLARE @hotel_capacity INT;
    DECLARE @new_capacity INT;
	DECLARE @total_price BIGINT;
	DECLARE @old_room_price BIGINT;

SELECT @hotel_capacity = total_capacity, @total_price = total_capacity * price_per_person
FROM hotel_detail
WHERE hotel_id = (SELECT hotel_id FROM deleted);

SET @new_capacity = @hotel_capacity - (SELECT num_people FROM deleted);
SET @old_room_price = (SELECT price FROM deleted);

UPDATE hotel_detail
SET total_capacity = @new_capacity,
    price_per_person = (@total_price - @old_room_price) / @new_capacity
WHERE hotel_id = (SELECT hotel_id FROM deleted);

END;