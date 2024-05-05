DROP TRIGGER after_room_insert;
--TRIGGER ADD ROOM
CREATE TRIGGER after_room_insert
    ON room
    AFTER INSERT
AS
BEGIN
    DECLARE @hotel_capacity INT;
    DECLARE @new_capacity INT;
    DECLARE @new_price BIGINT;
	DECLARE @num_people INT;

SELECT @hotel_capacity = total_capacity
FROM hotel_detail
WHERE hotel_id = (SELECT hotel_id FROM inserted);

SET @num_people = (SELECT num_people FROM inserted);

SET @new_capacity = @hotel_capacity + @num_people;

SET @new_price = (SELECT MIN(price / @num_people) FROM ROOM WHERE hotel_id = (SELECT hotel_id FROM inserted));

UPDATE hotel_detail
SET total_capacity = @new_capacity,
    price_per_person = @new_price
WHERE hotel_id = (SELECT hotel_id FROM inserted);
END;