alter table ROOM
add booked_guests int default 0

update ROOM set booked_guests = 1