CREATE SCHEMA hotel;

CREATE TABLE hotel.Users
(
  userID     IDENTITY PRIMARY KEY,
  login      VARCHAR(50)  NOT NULL UNIQUE,
  password   VARCHAR(100)  NOT NULL,
  permission VARCHAR(5) DEFAULT 'USER' CHECK (permission in ('USER', 'ADMIN')),
  firstName  VARCHAR(50)  NOT NULL,
  lastName   VARCHAR(50) NOT NULL
);

CREATE TABLE hotel.Rooms
(
  roomID     IDENTITY PRIMARY KEY,
  roomNumber SMALLINT NOT NULL UNIQUE,
  classID    VARCHAR(10) DEFAULT 'STANDARD' CHECK (classID IN ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX')),
  capacity   VARCHAR(10) DEFAULT 'SINGLE' CHECK (capacity IN ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD')),
  price      DECIMAL  NOT NULL
);

CREATE TABLE hotel.Requests
(
  requestID     IDENTITY PRIMARY KEY,
  userID        BIGINT NOT NULL,
  capacity      VARCHAR(10) DEFAULT 'SINGLE' CHECK (capacity IN ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD'))  ,
  classID       VARCHAR(10) DEFAULT 'STANDARD' CHECK (classID IN ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX')) ,
  checkIn       TIMESTAMP NOT NULL,
  checkOut      TIMESTAMP NOT NULL,
  paymentStatus VARCHAR(10) DEFAULT 'NOBILL' CHECK (paymentStatus IN ('PAID', 'BILLSENT', 'NOBILL'))
);

CREATE TABLE hotel.ReservedRooms
(
  reservedRoomID IDENTITY PRIMARY KEY,
  roomID         BIGINT REFERENCES hotel.Rooms (roomID),
  requestID      BIGINT REFERENCES hotel.Requests (requestID)
);
