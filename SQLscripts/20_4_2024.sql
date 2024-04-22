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



--TRIGGER ADD ROOM
CREATE TRIGGER after_room_insert
    ON room
    AFTER INSERT
AS
BEGIN

    DECLARE @hotel_capacity INT;
    DECLARE @new_capacity INT;

SELECT @hotel_capacity = total_capacity
FROM hotel_detail
WHERE hotel_id = (SELECT hotel_id FROM inserted);

SET @new_capacity = @hotel_capacity + (SELECT num_people FROM inserted);

UPDATE hotel_detail
SET total_capacity = @new_capacity
WHERE hotel_id = (SELECT hotel_id FROM inserted);
END;



ALTER TABLE ROOM
DROP CONSTRAINT DF__ROOM__is_booked__6477ECF3;

alter table ROOM
drop column is_booked
