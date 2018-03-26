package com.android.outbaselibrary.utils;

import android.content.Context;
import android.os.Environment;

import com.android.outbaselibrary.primary.AppContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveReport {

    private static final String TAG = "SaveReport";

    private static boolean checkSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    public static void infoLog(String key, String info) {


        String infoStr = "\r\n====Log:"
                + new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date()) + "====\r\n"
                + key + " : " + info + "\r\n";

        FileOutputStream outStream = null;
        try {

            if (checkSDCard()) {
                File file = new File(Environment.getExternalStorageDirectory().getPath()
                        + "/CrashLog.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                outStream = new FileOutputStream(file, true);
            } else {
                outStream = AppContext.getContext().openFileOutput("CrashLog.txt",
                        Context.MODE_APPEND);
            }

            outStream.write(infoStr.getBytes());
            outStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void crashLog(Throwable t) {

        String exceptionStr = "\r\n====Log:" + new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date()) + "====\r\n"
                + getExceptionInfo(t) + "\r\n";

        FileOutputStream outStream = null;
        try {

            if (checkSDCard()) {
                File file = new File(Environment.getExternalStorageDirectory().getPath()
                        + "/UcourseCrashLog.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                outStream = new FileOutputStream(file, true);
            } else {
                outStream = AppContext.getContext().openFileOutput("UcourseCrashLog.txt",
                        Context.MODE_APPEND);
            }

            outStream.write(exceptionStr.getBytes());
            outStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";
        }
    }

    private static String getExceptionInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }
}
