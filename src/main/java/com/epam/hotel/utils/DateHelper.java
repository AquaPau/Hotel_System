package com.epam.hotel.utils;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    private DateHelper() {
    }

    public static Date getTodayPlusDays(int days) {
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

}
