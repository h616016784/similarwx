package com.android.similarwx.utils;

import android.content.Context;

import com.android.similarwx.greendaodemo.gen.DaoMaster;
import com.android.similarwx.greendaodemo.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by hanhuailong on 2018/3/19.
 */

public class DBUtil {
    private static DBUtil mDbUtil;
    private DaoSession daoSession;
    private DBUtil(Context context){
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(context,"siml-db");
        Database db=devOpenHelper.getWritableDb();
        daoSession=new DaoMaster(db).newSession();
    }
    public static DBUtil getInstance(Context context){
        if (mDbUtil==null){
            synchronized (DBUtil.class){
                if (mDbUtil==null){
                    mDbUtil=new DBUtil(context);
                }
            }
        }
        return mDbUtil;
    }
    //返回app唯一的daosession
    public DaoSession getDaoSession() {
        return daoSession;
    }
}
