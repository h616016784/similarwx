package com.android.similarwx.model;


import com.android.similarwx.BuildConfig;

import java.io.File;


/**
 * Created by puyafeng on 2017/9/12.
 */

public interface APIConstants {
    String APP_KEY = "CEC228C8F519B96D25611051A3267185";
    String ENCODE_KEY = "CEC228C8F519B96D25611051A3267185CEC228C8F519B96D25611051A3267185";
    int IOS = 1;
    int ANDROID = 2;
    int BIZ_SUCCESS = 1;
    int BIZ_FAILUR = 0;
    String VERSION = "";
//    String DOMAIN_DEBUG = "http://39.105.107.104/api/learn" + File.separator + VERSION;
//    String DOMAIN_RELEASE = "http://39.105.107.104/api/learn" + File.separator + VERSION;
    String DOMAIN_DEBUG = "http://39.105.107.104/api"+File.separator;
    String DOMAIN_RELEASE = "http://39.105.107.104/api"+File.separator;
    String DOMAIN = BuildConfig.DEBUG ? DOMAIN_DEBUG : DOMAIN_RELEASE;

}
