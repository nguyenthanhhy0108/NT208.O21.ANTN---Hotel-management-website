CREATE TABLE CHAT_USER(
                          NickName VARCHAR(500) PRIMARY KEY,
                          UserName VARCHAR(500),
                          Status int
)

CREATE TABLE CHAT_MESSAGE(
                             ID INT PRIMARY KEY IDENTITY,
                             ChatId VARCHAR(500),
                             SenderId VARCHAR(500),
                             RecipientId VARCHAR(500),
                             Content VARCHAR(500)
                                 Timestamp date
)

CREATE TABLE CHAT_ROOM(
                          ID INT PRIMARY KEY IDENTITY,
                          ChatId VARCHAR(500),
                          SenderId VARCHAR(500),
                          RecipientId VARCHAR(500)
)

CREATE TABLE CHAT_NOTIFICATION(
                                  ID INT PRIMARY KEY IDENTITY,
                                  SenderId VARCHAR(100),
                                  RecipientId VARCHAR(100),
                                  is_Noticed int
)