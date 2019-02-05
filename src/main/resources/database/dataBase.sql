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
  capacity   hotel.capacity default 'SINGLE',
  price      MONEY NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.Requests
(
  requestID     UUID PRIMARY KEY,
  userID        UUID        NOT NULL REFERENCES hotel.Users (userID),
  capacity      hotel.capacity    default 'SINGLE',
  classID       hotel.classID     default 'STANDARD',
  checkIn       TIMESTAMPTZ NOT NULL,
  checkOut      TIMESTAMPTZ NOT NULL,
  paymentStatus hotel.paymentStatus default 'NOBILL'
);

CREATE TABLE IF NOT EXISTS hotel.ReservedRooms
(
  roomNumber SMALLINT PRIMARY KEY REFERENCES hotel.Rooms (roomNumber),
  requestID UUID REFERENCES hotel.Requests
);

DO $$
  BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'admin') THEN
      CREATE ROLE admin LOGIN PASSWORD 'admin';
    END IF;
  END
  $$;

GRANT ALL PRIVILEGES ON DATABASE Hotel TO ADMIN;

INSERT INTO hotel.Users values (1,'user1','1234',userID,'Petr','Ivanov');
INSERT INTO hotel.Users values (2,'user2','4321',userID,'Ivan','Pertov');

