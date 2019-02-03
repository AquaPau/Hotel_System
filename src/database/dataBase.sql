CREATE DATABASE Hotel;
CREATE SCHEMA schema;
CREATE TYPE permission AS ENUM ('ADMIN', 'USER');
CREATE TYPE classID AS ENUM ('ECONOM', 'STANDARD', 'FAMILY', 'LUXE');
CREATE TYPE capacity AS ENUM ('1', '2', '3', '4');
CREATE TYPE paymentStatus AS ENUM ('PAID', 'BILLSENT', 'NOBILL');


CREATE TABLE schema.Users
(
  userID     UUID PRIMARY KEY,
  login      VARCHAR(50)  NOT NULL UNIQUE,
  password   VARCHAR(20)  NOT NULL,
  permission permission default 'USER',
  firstName  varchar(50)  NOT NULL,
  lastName   varchar(100) NOT NULL
);

CREATE TABLE schema.Rooms
(
  roomNumber SMALLINT PRIMARY KEY,
  classID    classID  default 'STANDARD',
  capacity   capacity default '1',
  price      MONEY NOT NULL
);

CREATE TABLE schema.Requests
(
  requestID     UUID PRIMARY KEY,
  userID        UUID        NOT NULL REFERENCES Schema.Users (userID) ON UPDATE NO ACTION ON DELETE NO ACTION,
  capacity      capacity    default '1' REFERENCES schema.Rooms (capacity) ON UPDATE NO ACTION ON DELETE NO ACTION,
  classID       classID     default 'STANDARD' REFERENCES schema.Rooms (classID) ON UPDATE NO ACTION ON DELETE NO ACTION,
  checkIn       TIMESTAMPTZ NOT NULL,
  checkOut      TIMESTAMPTZ NOT NULL,
  paymentStatus paymentStatus default 'NOBILL'
);

CREATE TABLE schema.ReservedRooms
(
  roomNumber SMALLINT PRIMARY KEY REFERENCES Schema.Rooms (roomNumber) ON UPDATE NO ACTION ON DELETE NO ACTION,
  checkIn    TIMESTAMPTZ NOT NULL REFERENCES Schema.Requests (checkIn) ON UPDATE NO ACTION ON DELETE NO ACTION,
  checkOut   TIMESTAMPTZ NOT NULL REFERENCES Schema.Requests (checkOut) ON UPDATE NO ACTION ON DELETE NO ACTION,
  userID     UUID        NOT NULL REFERENCES Schema.Users (userID) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE USER ADMIN WITH PASSWORD 'ADMIN';
GRANT ALL PRIVILEGES ON DATABASE Hotel TO ADMIN;

