package com.android.similarwx.base;

import com.android.outbaselibrary.BaseApplication;
import com.android.outbaselibrary.utils.LogUtil;
import com.android.similarwx.greendaodemo.gen.DaoMaster;
import com.android.similarwx.greendaodemo.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by hanhuailong on 2018/3/15.
 */

public class AppApplication extends BaseApplication {
    private static AppApplication mInstance;
    private  DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        //初始化数据库greendao
        intDatabase();
    }

    private void intDatabase() {
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(this,"siml-db");
        Database db=devOpenHelper.getWritableDb();
        daoSession=new DaoMaster(db).newSession();
    }
    //返回app唯一的daosession
    public DaoSession getDaoSession() {
        return daoSession;
    }
    public static  AppApplication getInstance(){
        return mInstance;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.d("onTerminate");
    }
}
