package com.android.similarwx.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hanhuailong on 2018/7/3.
 */

public class TimeUtil {
    public static String timestampToString(long timestamp){
        return getDateToString(timestamp,"yyyy-MM-dd HH:mm:ss");
    }
    public static String timestampToString(long timestamp,String format){
        String tsStr = "";
        Timestamp ts = new Timestamp(timestamp);
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            //方法一
            tsStr = sdf.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
            return tsStr;
        }
        return tsStr;
    }

    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
