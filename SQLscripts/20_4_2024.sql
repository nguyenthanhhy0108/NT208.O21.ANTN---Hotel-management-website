ALTER TABLE HOTEL_DETAIL
ADD booking_count bigint;

update HOTEL_DETAIL
set booking_count = 1;