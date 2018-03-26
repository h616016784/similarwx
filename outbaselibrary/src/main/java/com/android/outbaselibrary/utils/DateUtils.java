package com.android.outbaselibrary.utils;

import com.android.outbaselibrary.primary.BaseConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Albert on 2016/8/23.
 */
public class DateUtils {

    public static long[] getNextMonthMillis() {

        long[] days = new long[30];

        long millis = System.currentTimeMillis();

        for (int i = 0; i < days.length; i++) {
            days[i] = millis;
            millis += 86400000;
        }

        return days;
    }

    public static String[] getNextMonthText() {

        String[] days = new String[30];

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        for (int i = 0; i < days.length; i++) {
            days[i] = displayDate(calendar);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return days;
    }

    public static String displayDate(Calendar calendar) {

        StringBuilder date = new StringBuilder();

        //        date.append(calendar.get(Calendar.YEAR)).append("年");
        date.append(calendar.get(Calendar.MONTH) + 1).append("月");
        date.append(calendar.get(Calendar.DAY_OF_MONTH)).append("日");
        date.append(" ").append(BaseConstants.WeekDay.fromValue(calendar.get(Calendar.DAY_OF_WEEK)));

        return date.toString();
    }

    public static String displayFormatted(String formatted) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(formatted);
            calendar.setTime(date);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return displayDate(calendar);
    }

    public static String formattedDate(Calendar calendar) {

        StringBuilder date = new StringBuilder();

        date.append(calendar.get(Calendar.YEAR)).append("-");
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (month.length() < 2) {
            month = "0" + month;
        }
        date.append(month).append("-");
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (day.length() < 2) {
            day = "0" + day;
        }
        date.append(day);

        return date.toString();
    }
}
