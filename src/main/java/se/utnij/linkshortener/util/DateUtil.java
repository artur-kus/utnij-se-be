package se.utnij.linkshortener.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date addDaysToDate(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        return calendar.getTime();
    }
}