DROP TABLE IF EXISTS ECHO_GAME;

CREATE TABLE ECHO_GAME (
                              id INT AUTO_INCREMENT  PRIMARY KEY,
                              isPrivate BOOLEAN NOT NULL,
                              playerID VARCHAR(250) NOT NULL,
                              lobbyID VARCHAR(250) NOT NULL,
                              timeGameCreated VARCHAR(250) DEFAULT NULL
);

INSERT INTO ECHO_GAME (isPrivate, playerID, lobbyID, timeGameCreated) VALUES
(false,'123454545', '123456789', '2020/1/9123');