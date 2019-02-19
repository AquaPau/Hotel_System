CREATE DATABASE Hotel;
CREATE SCHEMA IF NOT EXISTS hotel;

CREATE TABLE IF NOT EXISTS hotel.Users
(
  userID     BIGSERIAL PRIMARY KEY,
  login      VARCHAR(50)  NOT NULL UNIQUE,
  password   VARCHAR(100)  NOT NULL,
  permission VARCHAR(5) DEFAULT 'USER' CHECK (permission in ('USER', 'ADMIN')),
  firstName  VARCHAR(50)  NOT NULL,
  lastName   VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.Rooms
(
  roomID     BIGINT PRIMARY KEY,
  roomNumber SMALLINT NOT NULL UNIQUE,
  classID    VARCHAR(10) DEFAULT 'STANDARD' CHECK (classID IN ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX')),
  capacity   VARCHAR(10) DEFAULT 'SINGLE' CHECK (capacity IN ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD')),
  price      DECIMAL(19,4)    NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.Requests
(
  requestID     BIGSERIAL PRIMARY KEY,
  userID        BIGINT REFERENCES hotel.Users (userid),
  capacity      VARCHAR(10) DEFAULT 'SINGLE' CHECK (capacity IN ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD')),
  classID       VARCHAR(10) DEFAULT 'STANDARD' CHECK (classID IN ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX')),
  checkIn       TIMESTAMP NOT NULL,
  checkOut      TIMESTAMP NOT NULL,
  paymentStatus VARCHAR(10) DEFAULT 'NOBILL' CHECK (paymentStatus IN ('PAID', 'BILLSENT', 'NOBILL'))
);

CREATE TABLE IF NOT EXISTS hotel.ReservedRooms
(
  reservedRoomID BIGSERIAL PRIMARY KEY,
  roomNumber     BIGINT REFERENCES hotel.Rooms (roomID),
  requestID      BIGINT REFERENCES hotel.Requests (requestID)
);







