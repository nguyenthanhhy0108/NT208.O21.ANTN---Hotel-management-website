# 4/4/2024
create database NT;

use NT;

create table USERS(
                      Username varchar(50) primary key,
                      Password varchar(500) not null,
                      Enabled int not null
);

create table AUTHORITIES(
                            Username varchar(50) not null,
                            Authority varchar(50) not null,
                            Primary key (Username, Authority),
                            Foreign key (Username) references USERS(Username)
);

insert into USERS(Username, Password, Enabled) values ('22520593@gm.uit.edu.vn', '$2a$10$baoxjTClM2adP1Mdc9hDiexesFoY5miTDSAloR82FDtOH4l98XoJC', 1);

insert into AUTHORITIES(Username, Authority) values ('22520593@gm.uit.edu.vn', 'ROLE_ADMIN');
insert into AUTHORITIES(Username, Authority) values ('22520593@gm.uit.edu.vn', 'ROLE_USER');

create table USERDETAILS(
                            Username varchar(50) not null,
                            Name varchar(100),
                            Nationality varchar(50),
                            Address varchar(500),
                            PhoneNumber varchar(15),

                            primary key (Username),
                            foreign key (Username) references USERS(Username)
);

insert into USERDETAILS(Username, Name, Nationality, Address, PhoneNumber) values ('22520593@gm.uit.edu.vn', 'Nguyen Thanh Hy','Viet Nam', 'Chau Thanh Ben Tre', '0941609091');