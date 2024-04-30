create table REQUEST_OWNER(
                              request_id INT IDENTITY(1,1),
                              username varchar(50) not null,
                              request_message varchar(200),
                              is_accepted int default 0,
                              Primary key (request_id),
                              Foreign key (Username) references USERS(Username)
);