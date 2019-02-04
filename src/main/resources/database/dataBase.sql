CREATE DATABASE Hotel;
CREATE SCHEMA IF NOT EXISTS hotel;
DROP TYPE IF EXISTS hotel.permission;
CREATE TYPE hotel.permission AS ENUM ('ADMIN', 'USER');
DROP TYPE IF EXISTS hotel.classID;
CREATE TYPE hotel.classID AS ENUM ('ECONOM', 'STANDARD', 'FAMILY', 'LUXE');
DROP TYPE IF EXISTS hotel.capacity;
CREATE TYPE hotel.capacity AS ENUM ('1', '2', '3', '4');
DROP TYPE IF EXISTS hotel.paymentStatus;
CREATE TYPE hotel.paymentStatus AS ENUM ('PAID', 'BILLSENT', 'NOBILL');


CREATE TABLE IF NOT EXISTS hotel.Users
(
  userID     UUID PRIMARY KEY,
  login      VARCHAR(50)  NOT NULL UNIQUE,
  password   VARCHAR(20)  NOT NULL,
  permission hotel.permission default 'USER',
  firstName  varchar(50)  NOT NULL,
  lastName   varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.Rooms
(
  roomNumber SMALLINT PRIMARY KEY,
  classID    hotel.classID  default 'STANDARD',
  capacity   hotel.capacity default '1',
  price      MONEY NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.Requests
(
  requestID     UUID PRIMARY KEY,
  userID        UUID        NOT NULL REFERENCES hotel.Users (userID),
  capacity      hotel.capacity    default '1',
  classID       hotel.classID     default 'STANDARD' REFERENCES hotel.Rooms (classID),
  checkIn       TIMESTAMPTZ NOT NULL,
  checkOut      TIMESTAMPTZ NOT NULL,
  paymentStatus hotel.paymentStatus default 'NOBILL'
);

CREATE TABLE IF NOT EXISTS hotel.ReservedRooms
(
  roomNumber SMALLINT PRIMARY KEY REFERENCES hotel.Rooms (roomNumber),
  requestID UUID REFERENCES hotel.Requests
);

CREATE USER ADMIN WITH PASSWORD 'ADMIN';
GRANT ALL PRIVILEGES ON DATABASE Hotel TO ADMIN;

