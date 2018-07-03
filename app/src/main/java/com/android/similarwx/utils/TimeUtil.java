package com.android.similarwx.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by hanhuailong on 2018/7/3.
 */

public class TimeUtil {
    public static String timestampToString(long timestamp){
        return timestampToString(timestamp,"yyyy-mm-dd hh:mm:ss");
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
}
