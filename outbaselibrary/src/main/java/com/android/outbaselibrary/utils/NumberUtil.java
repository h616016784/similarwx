package com.android.outbaselibrary.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by Albert
 * on 16-6-2.
 */
public class NumberUtil {
    public static int toInt(String str) {
        return toInt(str, 0);
    }

    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static long toLong(String str) {
        return toLong(str, 0L);
    }

    public static long toLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static float toFloat(String str) {
        return toFloat(str, 0.0f);
    }

    public static float toFloat(String str, float defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    //过滤安卓手机号码里非数字
    private static Pattern mPhoneNumbersPattern = Pattern
            .compile("[\\+\\-\\*\\#\\,\\;\\(\\)\\.\\/ ]");

    public static String getPhoneNumbers(String content) {
        if (!TextUtils.isEmpty(content)) {
            return mPhoneNumbersPattern.matcher(content).replaceAll("");
        }
        return null;
    }

    /**
     * Converts a byte[2] to a short, in LITTLE_ENDIAN format
     *
     * @param argB1
     * @param argB2
     * @return
     */
    public static short getShortInLittleEndian(byte argB1, byte argB2) {
        /*
         * no 0xff on the last one to keep the sign
         */
        return (short) (argB1 & 0xff | (argB2 << 8));
    }

    /**
     * Converts a byte[2] to a short, in BIG_ENDIAN format
     *
     * @param argB1
     * @param argB2
     * @return
     */
    public static short getShortInBigEndian(byte argB1, byte argB2) {
        return (short) ((argB1 << 8) | (argB2 & 0xff));
    }

    public static short readShortInLittleEndian(final byte[] data, final int offset) {
        /*
         * no 0xff on the last one to keep the sign
         */
        return (short) ((data[offset] & 0xff) | (data[offset + 1] << 8));
    }

    public static int readIntInLittleEndian(final byte[] data, final int offset) {
        /*
         * no 0xff on the last one to keep the sign
         */
        return (data[offset] & 0xff) | ((data[offset + 1] & 0xff) << 8)
                | ((data[offset + 2] & 0xff) << 16) | (data[offset + 3] << 24);
    }

    public static long readLongInLittleEndian(final byte[] data, final int offset) {
        /*
         * no 0xff on the last one to keep the sign
         */
        return (data[offset] & 0xff) | ((data[offset + 1] & 0xff) << 8)
                | ((data[offset + 2] & 0xff) << 16) | ((data[offset + 3] & 0xff) << 24)
                | ((data[offset + 4] & 0xff) << 32) | ((data[offset + 5] & 0xff) << 40)
                | ((data[offset + 6] & 0xff) << 48) | (data[offset + 7] << 56);
    }

    public static String formatThousand(int number) {

        if (number >= 1000) {
            return "999+";
        }

        return String.valueOf(number);
    }

    public static String formatTenThousand(int number) {

        if (number > 10000) {
            return String.valueOf(1f * (number / 1000) / 10) + "万";
        }

        return String.valueOf(number);
    }

    public static String formatTenThousand(long number) {

        if (number > 10000) {
            return String.valueOf(1f * (number / 1000) / 10) + "万";
        }

        return String.valueOf(number);
    }
}
