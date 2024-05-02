alter table ROOM
add booked_guests int default 0

update ROOM set booked_guests = 1

alter table booking
add book_date DATE

update BOOKING set book_date = '2024/04/05'