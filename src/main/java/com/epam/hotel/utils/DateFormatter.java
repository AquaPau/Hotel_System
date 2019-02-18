package com.epam.hotel.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateFormatter {

    private DateFormatter() {
    }

    public static String convertDateToString(Timestamp timestamp) {
        int year = timestamp.toLocalDateTime().getYear();
        int month = timestamp.toLocalDateTime().getMonthValue();
        int day = timestamp.toLocalDateTime().getDayOfMonth();
        return String.format("%02d/%02d/%d", month, day, year);
    }

    public static Timestamp convertStringToDate(String date) {
        String[] split = date.split("/");
        int month = Integer.parseInt(split[0]);
        int day = Integer.parseInt(split[1]);
        int year = Integer.parseInt(split[2]);
        return Timestamp.valueOf(LocalDateTime.of(year, month, day, 0, 0));
    }

    public static long getDifferenceInDays(Timestamp date1, Timestamp date2) {
        LocalDateTime localDateTime1 = date1.toLocalDateTime();
        LocalDateTime localDateTime2 = date2.toLocalDateTime();
        return ChronoUnit.DAYS.between(localDateTime2, localDateTime1);
    }

}
