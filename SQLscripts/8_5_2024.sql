ALTER TRIGGER after_room_deleted
    ON room
    AFTER DELETE
AS
BEGIN
    DECLARE @hotel_capacity INT;
    DECLARE @new_capacity INT;
    DECLARE @new_price BIGINT;
    DECLARE @num_people INT;

SELECT @hotel_capacity = total_capacity
FROM hotel_detail
WHERE hotel_id = (SELECT hotel_id FROM deleted);

SET @num_people = (SELECT num_people FROM deleted);

SET @new_capacity = @hotel_capacity - @num_people;

SET @new_price = (SELECT MIN(price / num_people) FROM ROOM WHERE hotel_id = (SELECT hotel_id FROM deleted));

IF (SELECT count(*) FROM ROOM WHERE hotel_id = (SELECT hotel_id FROM deleted)) = 0
BEGIN
	SET @new_price = 0;
END


UPDATE hotel_detail
SET total_capacity = @new_capacity,
    price_per_person = @new_price
WHERE hotel_id = (SELECT hotel_id FROM deleted);
END;
