alter table BOOKING add is_paid int
update BOOKING set is_paid = 0

ALTER TABLE booking ADD is_rated INT DEFAULT 0;
update BOOKING set is_rated = 0