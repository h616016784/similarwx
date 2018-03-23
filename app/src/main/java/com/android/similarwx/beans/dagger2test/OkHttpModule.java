package com.android.similarwx.beans.dagger2test;

import com.android.similarwx.utils.netmodle.LogIntercept;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by hanhuailong on 2018/3/21.
 */

@Module
public class OkHttpModule {
    private int cacheSize;
    public OkHttpModule(int cacheSize){
        this.cacheSize=cacheSize;
    }
    @Provides
    OkHttpClient provideOkHttpClient(){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .cache(new Cache(new File(""),this.cacheSize))
                .build();
        return okHttpClient;
    }
}
