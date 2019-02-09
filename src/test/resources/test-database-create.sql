CREATE SCHEMA hotel;


CREATE TABLE hotel.Users
(
  userID     IDENTITY PRIMARY KEY,
  login      VARCHAR(50)  NOT NULL UNIQUE,
  password   VARCHAR(20)  NOT NULL,
  permission VARCHAR(5) DEFAULT 'USER' CHECK (permission IN ('USER', 'ADMIN')),
  firstName  VARCHAR(50)  NOT NULL,
  lastName   VARCHAR(100) NOT NULL
);

CREATE TABLE hotel.Rooms
(
  roomID     SMALLINT AUTO_INCREMENT PRIMARY KEY,
  roomNumber SMALLINT NOT NULL UNIQUE,
  classID    VARCHAR(10) DEFAULT 'STANDARD' CHECK (classID IN ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX')),
  capacity   VARCHAR(10) DEFAULT 'SINGLE' CHECK (capacity IN ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD')),
  price      DECIMAL  NOT NULL
);

CREATE TABLE hotel.Requests
(
  requestID     IDENTITY PRIMARY KEY,
  userID        BIGINT REFERENCES hotel.Users (userID),
  capacity      VARCHAR(10) DEFAULT 'SINGLE' CHECK (capacity IN ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD'))  ,
  classID       VARCHAR(10) DEFAULT 'STANDARD' CHECK (classID IN ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX')) ,
  checkIn       TIMESTAMP NOT NULL,
  checkOut      TIMESTAMP NOT NULL,
  paymentStatus VARCHAR(10) DEFAULT 'NOBILL' CHECK (paymentStatus IN ('PAID', 'BILLSENT', 'NOBILL'))
);

CREATE TABLE hotel.ReservedRooms
(
  reservedRoomID SMALLINT AUTO_INCREMENT PRIMARY KEY,
  roomNumber     SMALLINT REFERENCES hotel.Rooms (roomNumber),
  requestID      BIGINT REFERENCES hotel.Requests (requestID)
);
