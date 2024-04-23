use NT

alter table HOTEL
    add is_active int

--TRIGGER INSERT HOTEL
CREATE TRIGGER insert_hotel
    ON HOTEL
    FOR INSERT
AS
BEGIN
UPDATE HOTEL
SET is_active = 0
WHERE HOTEL.hotel_id IN (SELECT hotel_id FROM inserted);
END;


--TRIGGER INSERT HOTEL_CAPACITY_BOOKED
CREATE TRIGGER insert_HOTEL_CAPACITY_BOOKED
    ON HOTEL_CAPACITY_BOOKED
    FOR INSERT
AS
BEGIN
UPDATE HOTEL_CAPACITY_BOOKED
SET day1 = 0, day2 = 0, day3 = 0, day4 = 0, day5 = 0, day6 = 0, day7 = 0 , day8 = 0, day9 = 0, day10 = 0, day11 = 0, day12 = 0, day13 = 0, day14 = 0, day15 = 0, day16 = 0, day17 = 0, day18 = 0, day19 = 0, day20 = 0, day21 = 0, day22 = 0, day23 = 0, day24 = 0, day25 = 0, day26 = 0, day27 = 0, day28 = 0, day29 = 0, day30 = 0
WHERE HOTEL_CAPACITY_BOOKED.hotel_id IN (SELECT hotel_id FROM inserted);
END;

--TRIGGER UPDATE HOTEL
CREATE TRIGGER update_hotel
    ON HOTEL
    AFTER UPDATE
              AS
BEGIN
    IF (EXISTS (SELECT * FROM inserted))
BEGIN
INSERT INTO HOTEL_CAPACITY_BOOKED (hotel_id, day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14, day15, day16, day17, day18, day19, day20, day21, day22, day23, day24, day25, day26, day27, day28, day29, day30)
SELECT
    inserted.hotel_id,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
FROM
    inserted
WHERE
    inserted.is_active = 1;

DELETE HOTEL_CAPACITY_BOOKED WHERE hotel_id IN (SELECT hotel_id FROM inserted WHERE is_active = 0)
END;
END;

