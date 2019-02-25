CREATE DATABASE hotel;
CREATE SCHEMA IF NOT EXISTS hotel;

CREATE TABLE IF NOT EXISTS hotel.user
(
  id         BIGSERIAL PRIMARY KEY,
  login      VARCHAR(50)  NOT NULL UNIQUE,
  password   VARCHAR(100) NOT NULL,
  permission VARCHAR(5) DEFAULT 'USER' CHECK (permission in ('USER', 'ADMIN')),
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.room
(
  id       BIGSERIAL PRIMARY KEY,
  number   INT            NOT NULL UNIQUE,
  class    VARCHAR(10) DEFAULT 'STANDARD' CHECK (class IN ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX')),
  capacity VARCHAR(10) DEFAULT 'SINGLE' CHECK (capacity IN ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD')),
  price    DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.request
(
  id        BIGSERIAL PRIMARY KEY,
  user_id   BIGINT REFERENCES hotel.user (id),
  capacity  VARCHAR(10) DEFAULT 'SINGLE' CHECK (capacity IN ('SINGLE', 'DOUBLE', 'TRIPLE', 'QUAD')),
  class     VARCHAR(10) DEFAULT 'STANDARD' CHECK (class IN ('ECONOMY', 'STANDARD', 'FAMILY', 'LUX')),
  check_in  TIMESTAMP NOT NULL,
  check_out TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel.reservation
(
  request_id  BIGINT REFERENCES hotel.request (id),
  room_id     BIGINT REFERENCES hotel.room (id),
  status      VARCHAR(10) DEFAULT 'BILLSENT' CHECK (status IN ('BILLSENT', 'PAID', 'DENIED', 'CANCELLED')),
  total_price DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (request_id, room_id)
);

CREATE TABLE IF NOT EXISTS hotel.deny_message
(
  id        BIGSERIAL PRIMARY KEY,
  request_id  BIGINT REFERENCES hotel.request (id),
  message VARCHAR(255)
);