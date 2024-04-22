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



--TRIGGER ADD ROOM
CREATE TRIGGER after_room_insert
    ON room
    AFTER INSERT
AS
BEGIN

    DECLARE @hotel_capacity INT;
    DECLARE @new_capacity INT;

SELECT @hotel_capacity = current_capacity
FROM hotel_detail
WHERE hotel_id = (SELECT hotel_id FROM inserted);

SET @new_capacity = @hotel_capacity + (SELECT num_people FROM inserted);

UPDATE hotel_detail
SET current_capacity = @new_capacity
WHERE hotel_id = (SELECT hotel_id FROM inserted);
END;


--TRIGGER BOOK
CREATE TRIGGER after_room_booked
    ON room
    AFTER update, insert
          AS
BEGIN

    DECLARE @hotel_capacity INT;
    DECLARE @new_capacity INT;
	DECLARE @is_booked INT;

SELECT @is_booked = is_booked from inserted
                                       if (@is_booked = 1)
BEGIN
SELECT @hotel_capacity = current_capacity
FROM hotel_detail
WHERE hotel_id = (SELECT hotel_id FROM inserted);

SET @new_capacity = @hotel_capacity - (SELECT num_people FROM inserted);

UPDATE hotel_detail
SET current_capacity = @new_capacity
WHERE hotel_id = (SELECT hotel_id FROM inserted);
END
END;

