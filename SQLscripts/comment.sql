create table COMMENT(
                           user_name VARCHAR(50),
                           star INT,
                           comment_id INT IDENTITY(1,1),
                           comment varchar(500),
                           room_id VARCHAR(50),
                           Primary key (comment_id),
                           FOREIGN KEY (user_name) references USERS(Username)
);
