package com.android.outbaselibrary.primary;

/**
 * Created by hanhuailong on 2018/3/26.
 */

public class BaseConstants {
    public static enum WeekDay {

        SUNDAY(1, "周日"), //
        MONDAY(2, "周一"), //
        TUESDAY(3, "周二"), //
        WEDNESDAY(4, "周三"), //
        THURSDAY(5, "周四"), //
        FRIDAY(6, "周五"), //
        SATURDAY(7, "周六"); //

        private int mValue;
        private String mText;

        WeekDay(int value, String text) {
            mValue = value;
            mText = text;
        }

        public static String fromValue(int value) {

            String text = null;

            switch (value) {
                case 1:
                    text = SUNDAY.getText();
                    break;
                case 2:
                    text = MONDAY.getText();
                    break;
                case 3:
                    text = TUESDAY.getText();
                    break;
                case 4:
                    text = WEDNESDAY.getText();
                    break;
                case 5:
                    text = THURSDAY.getText();
                    break;
                case 6:
                    text = FRIDAY.getText();
                    break;
                case 7:
                    text = SATURDAY.getText();
                    break;
            }

            return text;
        }

        public int getValue() {
            return mValue;
        }

        public String getText() {
            return mText;
        }
    }

    public static enum Gender {

        Male(0, "男"), //
        Female(1, "女"); //

        private int mValue;
        private String mText;

        Gender(int value, String text) {
            mValue = value;
            mText = text;
        }

        public static String fromValue(int value) {

            String text = null;

            switch (value) {
                case 1:
                    text = Male.getText();
                    break;
                case 2:
                    text = Female.getText();
                    break;
            }

            return text;
        }

        public int getValue() {
            return mValue;
        }

        public String getText() {
            return mText;
        }
    }

}
