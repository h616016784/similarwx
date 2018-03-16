package com.android.similarwx.utils.netmodle;

import com.android.similarwx.BuildConfig;

import java.io.File;

/**
 * Created by hanhuailong on 2018/3/15.
 */

public class ServiceConstants {
    public static final String VERSION = "v1"+ File.separator;
    public static final String DOMAIN_DEBUG = "http://www.baidu.com" + File.separator + VERSION;
    public static final String DOMAIN_RELEASE = "" + File.separator +VERSION;
    public static final String DOMAIN = BuildConfig.DEBUG ? DOMAIN_DEBUG : DOMAIN_RELEASE;

    private static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024;
}
