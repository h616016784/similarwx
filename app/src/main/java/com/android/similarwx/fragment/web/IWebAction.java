package com.android.similarwx.fragment.web;

import java.io.File;
import java.util.List;

/**
 * Created by puyafeng on 2017/7/6.
 */

public interface IWebAction {
    /**
     * 支持h5截图
     * @param absPath
     * @return
     */
    File capture(String absPath);

    /**
     * 支持基本查找
     * @param tag
     * @return
     */
    List<String> find(String tag);


    void loadUrl(String url);
}
