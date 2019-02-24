CREATE DATABASE hotel;
CREATE SCHEMA IF NOT EXISTS hotel;

CREATE TABLE IF NOT EXISTS hotel.user
(
  id         BIGSERIAL PRIMARY KEY,
  login      VARCHAR(50)  NOT NULL UNIQUE,
  password   VARCHAR(100) NOT NULL,
  permission VARCHAR(5) DEFAULT 'USER' CHECK (permission in ('USER', 'ADMIN')),
  firstname  VARCHAR(255) NOT NULL,
  lastname   VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.room
(
  id       BIGSERIAL PRIMARY KEY,
  number   INT            NOT NULL UNIQUE,
  class    VARCHAR(10) DEFAULT 'STANDARD' CHECK (class IN ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX')),
  capacity VARCHAR(10) DEFAULT 'SINGLE' CHECK (capacity IN ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD')),
  price    DECIMAL(19, 4) NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.request
(
  id            BIGSERIAL PRIMARY KEY,
  user_id       BIGINT REFERENCES hotel.user (id),
  room_id       BIGINT REFERENCES hotel.room (id),
  capacity      VARCHAR(10) DEFAULT 'SINGLE' CHECK (capacity IN ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD')),
  class         VARCHAR(10) DEFAULT 'STANDARD' CHECK (class IN ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX')),
  checkin       TIMESTAMP NOT NULL,
  checkout      TIMESTAMP NOT NULL,
  paymentstatus VARCHAR(10) DEFAULT 'NOBILL' CHECK (paymentstatus IN ('PAID', 'BILLSENT', 'NOBILL'))
);