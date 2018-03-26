package com.android.outbaselibrary.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanhuailong on 2017/3/11.
 */
public class ActivityController {
    private static volatile ActivityController mSingleton;
    private List<Activity> mListActs;

    private ActivityController() {
        mListActs = new ArrayList<>();
    }

    public static ActivityController getInstance() {
        if (mSingleton == null) {
            synchronized (ActivityController.class) {
                if (mSingleton == null) {
                    mSingleton = new ActivityController();
                }
            }
        }
        return mSingleton;
    }

    /**
     * 获取指定位置的Activity
     * @param position
     * @param <T>
     * @return
     */
    public <T extends Activity> T find(int position) {
        if (mListActs != null && mListActs.size() > position) {
            return (T)mListActs.get(position);
        } else {
            return null;
        }
    }

    /**
     * 获取顶部的Activity
     * @param <T>
     * @return
     */
    public <T extends Activity> T findTop(){
        if(CollectionUtil.isEmpty(mListActs)){
            return null;
        }
        return (T) mListActs.get(mListActs.size()-1);
    }

    /**
     * 获取栈中指定类型的Activity
     * @param pClass
     * @param <T>
     * @return
     */
    public <T extends Activity> T find(Class<T> pClass) {
        if (mListActs == null) {
            return null;
        }
        Activity act = null;
        for (Activity item:mListActs) {
            if (item.getClass().equals(pClass)) {
                act = item;
            }
        }
        return (T)act;
    }

    /**
     * 只允许同包下调用
     *
     * @param pActivity
     */
    public void add(Activity pActivity) {
        if (mListActs != null && pActivity != null) {
            mListActs.add(pActivity);
        }
    }

    public void remove(Activity pActivity) {
        if (mListActs != null) {
            pActivity.finish();
            mListActs.remove(pActivity);
        }
    }

    /**
     * 从集合中关闭并移除指定位置activity
     * @param piPosition
     */
    public void remove(int piPosition) {
        if (mListActs != null && mListActs.size() > piPosition) {
            Activity act = mListActs.get(piPosition);
            act.finish();
            mListActs.remove(act);
        }
    }

    /**
     * 从集合中关闭并移除指定activity
     * @param pClass
     */
    public void remove(Class<?> pClass) {
        if (mListActs == null) {
            return;
        }
        for (Activity act:mListActs) {
            if(act.getClass().equals(pClass))
            act.finish();
            mListActs.remove(act);
        }
    }

    /**
     * 关闭所有activity
     */
    public void removeAll() {
        for (Activity act : mListActs) {
            if (act != null) {
                act.finish();
            }
        }
        if (mListActs != null) {
            mListActs.clear();
        }
    }


}
