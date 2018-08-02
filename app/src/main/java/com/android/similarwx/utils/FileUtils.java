package com.android.similarwx.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by puyafeng on 2017/5/16.
 */

public class FileUtils {
    /***
     * sd卡上创建文件
     */
    public static File createFileInSD(String name) {
        try {
            File dir;
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            {
                dir = new File(Environment.getExternalStorageDirectory() + "");
            }else {
                dir = new File(Environment.getDataDirectory()+"");
            }
            File file = new File(dir + "/" + name);

            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取文本内容
     *
     * @return
     */
    public static String getText(String path) {
        String str = "";
        BufferedReader reader = null;
        try {
            // 通过InputStreamReader的方式进行文件的去读，并且设置按照什么样的编码格式进行文件读取
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("gbk")));
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    str += line + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

}
