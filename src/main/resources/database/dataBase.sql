SELECT exists(SELECT datname FROM pg_catalog.pg_database where datname='Hotel');
CREATE SCHEMA IF NOT EXISTS hotel;
DROP TYPE IF EXISTS hotel.permission CASCADE;
CREATE TYPE hotel.permission AS ENUM ('ADMIN', 'USER');
DROP TYPE IF EXISTS hotel.classID CASCADE ;
CREATE TYPE hotel.classID AS ENUM ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX');
DROP TYPE IF EXISTS hotel.capacity CASCADE ;
CREATE TYPE hotel.capacity AS ENUM ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD');
DROP TYPE IF EXISTS hotel.paymentStatus CASCADE ;
CREATE TYPE hotel.paymentStatus AS ENUM ('PAID', 'BILLSENT', 'NOBILL');


CREATE TABLE IF NOT EXISTS hotel.Users
(
  userID     BIGSERIAL PRIMARY KEY ,
  login      VARCHAR(50)  NOT NULL UNIQUE,
  password   VARCHAR(20)  NOT NULL,
  permission hotel.permission default 'USER',
  firstName  varchar(50)  NOT NULL,
  lastName   varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.Rooms
(
  roomID     SMALLSERIAL PRIMARY KEY,
  roomNumber SMALLINT NOT NULL UNIQUE,
  classID    hotel.classID  default 'STANDARD',
  capacity   hotel.capacity default 'SINGLE',
  price      MONEY NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.Requests
(
  requestID     BIGSERIAL PRIMARY KEY,
  userID        BIGINT REFERENCES hotel.Users (userID),
  capacity      hotel.capacity    default 'SINGLE',
  classID       hotel.classID     default 'STANDARD',
  checkIn       TIMESTAMPTZ NOT NULL,
  checkOut      TIMESTAMPTZ NOT NULL,
  paymentStatus hotel.paymentStatus default 'NOBILL'
);

CREATE TABLE IF NOT EXISTS hotel.ReservedRooms
(
  reservedRoomID SMALLSERIAL PRIMARY KEY ,
  roomNumber SMALLINT REFERENCES hotel.Rooms (roomNumber),
  requestID BIGINT REFERENCES hotel.Requests (requestID)
);








