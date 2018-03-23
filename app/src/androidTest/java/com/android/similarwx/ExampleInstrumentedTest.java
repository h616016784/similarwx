package com.android.similarwx;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.similarwx.beans.DbUser;
import com.android.similarwx.utils.DBUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.android.similarwx", appContext.getPackageName());
    }
    @Test
    public void listUsers() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        DbUser dbUser=new DbUser();
        dbUser.setName("test3");
        DBUtil.getInstance(appContext).getDaoSession().insert(dbUser);
        List<DbUser> list= DBUtil.getInstance(appContext).getDaoSession().queryBuilder(DbUser.class).list();

        assertEquals(1,list.size());
//        assertEquals("hhl",list.get(0).getName());
    }
}
