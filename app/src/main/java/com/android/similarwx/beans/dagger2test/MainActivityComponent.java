package com.android.similarwx.beans.dagger2test;

import com.android.similarwx.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hanhuailong on 2018/3/21.
 */
@Singleton
@Component(modules = OkHttpModule.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
