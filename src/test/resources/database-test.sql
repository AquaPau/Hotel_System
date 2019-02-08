CREATE SCHEMA hotel;

CREATE TABLE IF NOT EXISTS hotel.Users
(
  userID     IDENTITY PRIMARY KEY,
  login      VARCHAR(50)  NOT NULL UNIQUE,
  password   VARCHAR(20)  NOT NULL,
  permission ENUM ('ADMIN', 'USER') default 'USER',
  firstName  varchar(50)  NOT NULL,
  lastName   varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.Rooms
(
  roomID     SMALLINT AUTO_INCREMENT PRIMARY KEY,
  roomNumber SMALLINT NOT NULL UNIQUE,
  classID    ENUM ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX') default 'STANDARD',
  capacity   ENUM ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD')   default 'SINGLE',
  price      DECIMAL  NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.Requests
(
  requestID     IDENTITY PRIMARY KEY,
  userID        BIGINT REFERENCES hotel.Users (userID),
  capacity      ENUM ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD')   default 'SINGLE',
  classID       ENUM ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX') default 'STANDARD',
  checkIn       TIMESTAMP NOT NULL,
  checkOut      TIMESTAMP NOT NULL,
  paymentStatus ENUM ('PAID', 'BILLSENT', 'NOBILL')           default 'NOBILL'
);

CREATE TABLE IF NOT EXISTS hotel.ReservedRooms
(
  reservedRoomID SMALLINT AUTO_INCREMENT PRIMARY KEY,
  roomNumber     SMALLINT REFERENCES hotel.Rooms (roomNumber),
  requestID      BIGINT REFERENCES hotel.Requests (requestID)
);