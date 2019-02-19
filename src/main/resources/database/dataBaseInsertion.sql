/* Generating data for database: hotel  */

/* Table hotel.requests cleared */
TRUNCATE TABLE "hotel"."requests" RESTART IDENTITY CASCADE ;
COMMIT;

/* Table hotel.users cleared */
TRUNCATE TABLE "hotel"."users" RESTART IDENTITY CASCADE;
COMMIT;

/* Table hotel.rooms cleared */
TRUNCATE TABLE "hotel"."rooms" RESTART IDENTITY CASCADE;
COMMIT;

/* Generating data for table hotel.users... */
INSERT INTO "hotel"."users"("login","password","permission","firstname","lastname")
VALUES(E'user00',E'$2a$11$nbhGiWLEgfRWMdrcvOAHK.dWWAERJH6MeOqHXYmWGQ2WIOCxdf1tq',E'USER',E'Aqakw',E'Ykmgzsan');
INSERT INTO "hotel"."users"("login","password","permission","firstname","lastname")
VALUES(E'user01',E'$2a$11$W1EnX.KpC2no4renbBKHwOZbbJO8ZaoD1GD3U9tnrjvAZXCCFUW92',E'USER',E'Tsdce',E'Wotbxdqv');
INSERT INTO "hotel"."users"("login","password","permission","firstname","lastname")
VALUES(E'user02',E'$2a$11$p3ZZAmPXIXUxfJ5Y3eWZK.DuDzy3vIlSzQDLyK3u.bcU03Fe7qZPq',E'USER',E'Uhewa',E'Wpddkqel');
INSERT INTO "hotel"."users"("login","password","permission","firstname","lastname")
VALUES(E'user03',E'$2a$11$TTv6.t/LJA.XvxmFtMaAqOiA0Ln3j0qdWgwYim8K8qYf8CpCOBfWG',E'USER',E'Gpnaf',E'Jgzywlsi');
INSERT INTO "hotel"."users"("login","password","permission","firstname","lastname")
VALUES(E'user04',E'$2a$11$TCagtH.ZrUTuP1P2xrHqtOBLJtopHdrcBu6d8NyUy8bE8vqdoKArK',E'USER',E'Eavhd',E'Dseqjkth');
INSERT INTO "hotel"."users"("login","password","permission","firstname","lastname")
VALUES(E'user05',E'$2a$11$pM6Je4bgRjwujHuWCC.gjuK2LyUfrMKuUrPmccs9mx8ZEMc2Q8hiK',E'USER',E'Xjeoo',E'Icyhklnx');
INSERT INTO "hotel"."users"("login","password","permission","firstname","lastname")
VALUES(E'user06',E'$2a$11$r8S3swC925lKc9pG/UJlCedwdeXEwq3Dshpedu6A3sy6X9BUzOcNG',E'USER',E'Kiilz',E'Pyysgxtc');
INSERT INTO "hotel"."users"("login","password","permission","firstname","lastname")
VALUES(E'user07',E'$2a$11$pbPErEfDgUvBC3hcUFiBuewBNcCaB2Ud5ip69/oDtoIu2bAYFEF.i',E'USER',E'Qpkvx',E'Bxqnkbxh');
INSERT INTO "hotel"."users"("login","password","permission","firstname","lastname")
VALUES(E'user08',E'$2a$11$to9jtr6gQHLZi0F8J5JHmuq/KwHPCR.gU4slEXn31ObHNGZiZONI.',E'USER',E'Geoos',E'Tzgyngbc');
INSERT INTO "hotel"."users"("login","password","permission","firstname","lastname")
VALUES(E'user09',E'$2a$11$CrBzzW4TxZUJXtmT2YekYOAB0BstH.WAwUT5K9SKZDGd4/HBzbslO',E'USER',E'Fpsrs',E'Zkmiypzy');
INSERT INTO "hotel"."users"("login","password","permission","firstname","lastname")
VALUES(E'admin',E'$2a$11$IzFNh9Fapotg88wOhUDyBupIUE/BZH9i/UwkrMPA1cAL5gknfXVH.',E'ADMIN',E'Lul',E'Kekov');
COMMIT;

/* Generating data for table hotel.rooms... */
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(1,E'FAMILY',E'SINGLE',4833.84);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(2,E'ECONOMY',E'SINGLE',4632.76);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(3,E'STANDARD',E'QUAD',519.7);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(4,E'LUX',E'SINGLE',8414.86);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(5,E'ECONOMY',E'SINGLE',8030.58);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(6,E'STANDARD',E'TRIPLE',7182.1);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(7,E'ECONOMY',E'SINGLE',6333.32);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(8,E'FAMILY',E'SINGLE',9556.79);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(9,E'LUX',E'DOUBLE',2805.26);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(10,E'FAMILY',E'DOUBLE',3336.85);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(11,E'FAMILY',E'SINGLE',768.2);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(12,E'ECONOMY',E'SINGLE',9798.2);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(13,E'STANDARD',E'QUAD',1499.52);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(14,E'FAMILY',E'DOUBLE',5979.13);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(15,E'ECONOMY',E'DOUBLE',7914.97);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(16,E'STANDARD',E'QUAD',5490.74);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(17,E'LUX',E'TRIPLE',5313.64);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(18,E'LUX',E'TRIPLE',4472.78);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(19,E'ECONOMY',E'QUAD',2207.88);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(20,E'STANDARD',E'QUAD',5647.62);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(21,E'LUX',E'DOUBLE',3317.58);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(22,E'ECONOMY',E'QUAD',7317.65);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(23,E'STANDARD',E'DOUBLE',3533.19);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(24,E'LUX',E'DOUBLE',704.94);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(25,E'FAMILY',E'SINGLE',4705.36);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(26,E'FAMILY',E'TRIPLE',4849.01);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(27,E'STANDARD',E'QUAD',8093.18);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(28,E'STANDARD',E'DOUBLE',6540.84);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(29,E'ECONOMY',E'TRIPLE',8213.56);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(30,E'ECONOMY',E'QUAD',4144.62);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(31,E'LUX',E'TRIPLE',4378.43);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(32,E'LUX',E'TRIPLE',7906.82);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(33,E'LUX',E'TRIPLE',8860.92);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(34,E'ECONOMY',E'SINGLE',1454.41);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(35,E'STANDARD',E'DOUBLE',2376.06);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(36,E'FAMILY',E'QUAD',3886.88);
INSERT INTO "hotel"."rooms"("roomnumber","classid","capacity","price")
VALUES(37,E'FAMILY',E'TRIPLE',5047.76);
COMMIT;

/* Generating data for table hotel.requests... */
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(2,E'DOUBLE',E'FAMILY','2019-05-27 00:00:00.000000','2019-05-27 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(6,E'SINGLE',E'STANDARD','2019-05-20 00:00:00.000000','2019-05-20 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(5,E'DOUBLE',E'FAMILY','2019-04-25 00:00:00.000000','2019-04-25 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(3,E'TRIPLE',E'FAMILY','2019-05-19 00:00:00.000000','2019-05-19 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(11,E'TRIPLE',E'LUX','2019-06-02 00:00:00.000000','2019-06-02 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(10,E'QUAD',E'LUX','2019-06-05 00:00:00.000000','2019-06-05 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(7,E'QUAD',E'FAMILY','2019-06-06 00:00:00.000000','2019-06-06 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(9,E'SINGLE',E'LUX','2019-04-20 00:00:00.000000','2019-04-20 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(1,E'DOUBLE',E'STANDARD','2019-06-25 00:00:00.000000','2019-06-25 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(6,E'DOUBLE',E'ECONOMY','2019-04-14 00:00:00.000000','2019-04-14 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(9,E'QUAD',E'LUX','2019-05-09 00:00:00.000000','2019-05-09 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(11,E'DOUBLE',E'FAMILY','2019-05-25 00:00:00.000000','2019-05-25 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(3,E'DOUBLE',E'ECONOMY','2019-05-03 00:00:00.000000','2019-05-03 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(6,E'DOUBLE',E'ECONOMY','2019-05-05 00:00:00.000000','2019-05-05 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(9,E'SINGLE',E'LUX','2019-05-12 00:00:00.000000','2019-05-12 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(9,E'DOUBLE',E'LUX','2019-05-23 00:00:00.000000','2019-05-23 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(8,E'SINGLE',E'LUX','2019-04-17 00:00:00.000000','2019-04-17 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(7,E'TRIPLE',E'STANDARD','2019-06-26 00:00:00.000000','2019-06-26 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(2,E'QUAD',E'LUX','2019-06-11 00:00:00.000000','2019-06-11 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(10,E'QUAD',E'LUX','2019-04-09 00:00:00.000000','2019-04-09 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(6,E'QUAD',E'LUX','2019-04-14 00:00:00.000000','2019-04-14 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(5,E'SINGLE',E'FAMILY','2019-05-15 00:00:00.000000','2019-05-15 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(11,E'TRIPLE',E'ECONOMY','2019-05-12 00:00:00.000000','2019-05-12 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(1,E'TRIPLE',E'ECONOMY','2019-04-28 00:00:00.000000','2019-04-28 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(3,E'DOUBLE',E'ECONOMY','2019-05-29 00:00:00.000000','2019-05-29 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(4,E'SINGLE',E'STANDARD','2019-06-30 00:00:00.000000','2019-06-30 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(6,E'SINGLE',E'ECONOMY','2019-06-06 00:00:00.000000','2019-06-06 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(2,E'SINGLE',E'LUX','2019-06-11 00:00:00.000000','2019-06-11 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(11,E'DOUBLE',E'FAMILY','2019-06-19 00:00:00.000000','2019-06-19 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(9,E'DOUBLE',E'LUX','2019-06-25 00:00:00.000000','2019-06-25 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(7,E'SINGLE',E'LUX','2019-04-29 00:00:00.000000','2019-04-29 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(2,E'QUAD',E'FAMILY','2019-06-08 00:00:00.000000','2019-06-08 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(7,E'DOUBLE',E'STANDARD','2019-04-09 00:00:00.000000','2019-04-09 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(1,E'SINGLE',E'LUX','2019-05-18 00:00:00.000000','2019-05-18 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(9,E'TRIPLE',E'ECONOMY','2019-05-21 00:00:00.000000','2019-05-21 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(10,E'SINGLE',E'ECONOMY','2019-04-17 00:00:00.000000','2019-04-17 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(10,E'SINGLE',E'FAMILY','2019-06-09 00:00:00.000000','2019-06-09 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(7,E'QUAD',E'STANDARD','2019-06-14 00:00:00.000000','2019-06-14 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(8,E'QUAD',E'FAMILY','2019-05-24 00:00:00.000000','2019-05-24 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(11,E'DOUBLE',E'FAMILY','2019-04-03 00:00:00.000000','2019-04-03 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(9,E'TRIPLE',E'ECONOMY','2019-06-08 00:00:00.000000','2019-06-08 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(10,E'TRIPLE',E'FAMILY','2019-06-29 00:00:00.000000','2019-06-29 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(3,E'SINGLE',E'FAMILY','2019-05-21 00:00:00.000000','2019-05-21 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(5,E'TRIPLE',E'FAMILY','2019-05-11 00:00:00.000000','2019-05-11 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(4,E'QUAD',E'FAMILY','2019-05-18 00:00:00.000000','2019-05-18 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(5,E'TRIPLE',E'ECONOMY','2019-05-10 00:00:00.000000','2019-05-10 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(5,E'QUAD',E'FAMILY','2019-06-10 00:00:00.000000','2019-06-10 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(4,E'QUAD',E'LUX','2019-06-22 00:00:00.000000','2019-06-22 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(2,E'QUAD',E'ECONOMY','2019-04-28 00:00:00.000000','2019-04-28 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(2,E'TRIPLE',E'FAMILY','2019-04-04 00:00:00.000000','2019-04-04 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(5,E'QUAD',E'ECONOMY','2019-06-25 00:00:00.000000','2019-06-25 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(8,E'SINGLE',E'LUX','2019-05-13 00:00:00.000000','2019-05-13 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(4,E'QUAD',E'STANDARD','2019-04-27 00:00:00.000000','2019-04-27 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(3,E'TRIPLE',E'STANDARD','2019-04-27 00:00:00.000000','2019-04-27 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(8,E'DOUBLE',E'STANDARD','2019-04-29 00:00:00.000000','2019-04-29 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(1,E'QUAD',E'STANDARD','2019-06-20 00:00:00.000000','2019-06-20 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(5,E'QUAD',E'FAMILY','2019-06-17 00:00:00.000000','2019-06-17 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(9,E'DOUBLE',E'ECONOMY','2019-05-19 00:00:00.000000','2019-05-19 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(8,E'QUAD',E'STANDARD','2019-06-20 00:00:00.000000','2019-06-20 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(1,E'QUAD',E'LUX','2019-06-17 00:00:00.000000','2019-06-17 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(11,E'SINGLE',E'STANDARD','2019-06-01 00:00:00.000000','2019-06-01 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(4,E'DOUBLE',E'ECONOMY','2019-04-09 00:00:00.000000','2019-04-09 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(8,E'DOUBLE',E'ECONOMY','2019-05-26 00:00:00.000000','2019-05-26 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(6,E'TRIPLE',E'LUX','2019-04-15 00:00:00.000000','2019-04-15 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(10,E'SINGLE',E'FAMILY','2019-06-29 00:00:00.000000','2019-06-29 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(2,E'DOUBLE',E'FAMILY','2019-05-18 00:00:00.000000','2019-05-18 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(7,E'SINGLE',E'ECONOMY','2019-05-18 00:00:00.000000','2019-05-18 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(7,E'TRIPLE',E'STANDARD','2019-06-20 00:00:00.000000','2019-06-20 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(1,E'QUAD',E'ECONOMY','2019-06-05 00:00:00.000000','2019-06-05 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(6,E'TRIPLE',E'FAMILY','2019-06-07 00:00:00.000000','2019-06-07 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(10,E'SINGLE',E'STANDARD','2019-04-02 00:00:00.000000','2019-04-02 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(3,E'DOUBLE',E'STANDARD','2019-05-26 00:00:00.000000','2019-05-26 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(6,E'QUAD',E'ECONOMY','2019-06-06 00:00:00.000000','2019-06-06 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(4,E'TRIPLE',E'ECONOMY','2019-04-24 00:00:00.000000','2019-04-24 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(10,E'SINGLE',E'FAMILY','2019-06-25 00:00:00.000000','2019-06-25 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(6,E'TRIPLE',E'STANDARD','2019-05-23 00:00:00.000000','2019-05-23 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(1,E'DOUBLE',E'FAMILY','2019-06-26 00:00:00.000000','2019-06-26 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(3,E'SINGLE',E'ECONOMY','2019-05-02 00:00:00.000000','2019-05-02 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(10,E'SINGLE',E'LUX','2019-06-16 00:00:00.000000','2019-06-16 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(7,E'TRIPLE',E'STANDARD','2019-06-18 00:00:00.000000','2019-06-18 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(1,E'DOUBLE',E'STANDARD','2019-05-24 00:00:00.000000','2019-05-24 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(2,E'SINGLE',E'STANDARD','2019-04-21 00:00:00.000000','2019-04-21 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(1,E'QUAD',E'LUX','2019-04-15 00:00:00.000000','2019-04-15 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(5,E'TRIPLE',E'STANDARD','2019-05-26 00:00:00.000000','2019-05-26 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(8,E'TRIPLE',E'STANDARD','2019-04-30 00:00:00.000000','2019-04-30 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(2,E'QUAD',E'ECONOMY','2019-04-22 00:00:00.000000','2019-04-22 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(4,E'TRIPLE',E'LUX','2019-05-03 00:00:00.000000','2019-05-03 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(4,E'SINGLE',E'LUX','2019-04-18 00:00:00.000000','2019-04-18 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(7,E'TRIPLE',E'STANDARD','2019-05-15 00:00:00.000000','2019-05-15 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(11,E'TRIPLE',E'STANDARD','2019-04-07 00:00:00.000000','2019-04-07 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(9,E'QUAD',E'ECONOMY','2019-04-16 00:00:00.000000','2019-04-16 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(11,E'TRIPLE',E'FAMILY','2019-05-07 00:00:00.000000','2019-05-07 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(8,E'DOUBLE',E'LUX','2019-04-23 00:00:00.000000','2019-04-23 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(4,E'QUAD',E'ECONOMY','2019-06-23 00:00:00.000000','2019-06-23 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(11,E'SINGLE',E'ECONOMY','2019-06-10 00:00:00.000000','2019-06-10 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(8,E'DOUBLE',E'STANDARD','2019-05-28 00:00:00.000000','2019-05-28 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(5,E'TRIPLE',E'FAMILY','2019-06-13 00:00:00.000000','2019-06-13 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(3,E'SINGLE',E'LUX','2019-06-01 00:00:00.000000','2019-06-01 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(1,E'DOUBLE',E'LUX','2019-06-24 00:00:00.000000','2019-06-24 00:00:00.000000',E'NOBILL');
INSERT INTO "hotel"."requests"("userid","capacity","classid","checkin","checkout","paymentstatus")
VALUES(3,E'DOUBLE',E'STANDARD','2019-04-06 00:00:00.000000','2019-04-06 00:00:00.000000',E'NOBILL');
COMMIT;

UPDATE "hotel"."requests" SET checkout=checkout + (random() * 14 + 1 ) * interval '1 day';
COMMIT;