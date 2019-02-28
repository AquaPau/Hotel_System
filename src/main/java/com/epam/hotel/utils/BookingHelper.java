package com.epam.hotel.utils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BookingHelper {
    private BookingHelper() {
    }

    public static Date getTodayPlusDays(int days) {
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static BigDecimal countTotalPrice(Date checkIn, Date checkOut, BigDecimal roomPrice) {
        long diff = checkOut.getTime() - checkIn.getTime();
        BigDecimal days = BigDecimal.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        return roomPrice.multiply(days);
    }

}
