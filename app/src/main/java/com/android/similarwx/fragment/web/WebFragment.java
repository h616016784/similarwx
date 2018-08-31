package com.android.similarwx.fragment.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by puyafeng on 2017/7/6.
 */

public class WebFragment extends WebViewFragment implements IWebAction {
    JsInterface mJsInterface;
    String html;
    boolean isError = false;
    private ProgressCallback callback;

    /**
     * 截取webView快照(webView加载的整个内容的大小)
     *
     * @param webView
     * @return
     */
    public static Bitmap captureWebView(WebView webView) {
        Picture snapShot = webView.capturePicture();
        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        return bmp;
    }

    public static File saveBitmap(Context context, String picName, Bitmap bm) {
        String dir = Environment.getExternalStorageDirectory() + File.separator + "UTeacher";
        File df = new File(dir);
        if (!df.exists()) {
            df.mkdirs();
        }
        File f = new File(df, picName);
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            bm.recycle();
            // 其次把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    f.getAbsolutePath(), picName, null);
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + f.getAbsolutePath())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public void setProgressCallback(ProgressCallback callback){
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initWebView();
        return getWebView();
    }

    public void initWebView() {
        WebView wv = getWebView();
        if (wv != null) {
            initSettings(wv);
            initWebViewClient(wv);
            wv.setWebChromeClient(new WebChromeClient());
        }
    }

    private void initWebViewClient(WebView webView) {
        webView.setWebViewClient(new DefaultWebViewClient());

    }

    private void initSettings(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        mJsInterface = new JsInterface();
        webView.addJavascriptInterface(mJsInterface, JsInterface.NAME);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
        settings.setAllowFileAccess(true);// 设置允许访问文件数据
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDefaultTextEncodingName("uft-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //台电平板会在该代码出现WebViewClassic does not support TEXT_AUTOSIZING layout mode异常
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
    }

    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterface(Object obj, String name) {
        if (getWebView() != null) {
            getWebView().addJavascriptInterface(obj, name);
        }
    }

    /**
     * 设置js调用完毕监听
     *
     * @param onCallJsListener
     */
    public void addOnCallJsListener(JsInterface.OnCallJs onCallJsListener) {
        mJsInterface.setCallJsListener(onCallJsListener);
    }

    /**
     * 调用js
     *
     * @param js
     */
    public void callJs(String js) {
        JsInterface.callJs(getWebView(), js);
    }

    public void callJs(String js, JsInterface.OnCallJs callback) {
        addOnCallJsListener(callback);
        callJs(js);
    }

    /**
     * \
     * 通过asset文件名调用js方法
     *
     * @param context
     * @param assetName
     */
    public void callJsByAsset(Context context, String assetName) {
        JsInterface.callJsByAsset(context, getWebView(), assetName);
    }

    public void callJsByAsset(Context context, String assetName, JsInterface.OnCallJs callback) {
        addOnCallJsListener(callback);
        JsInterface.callJsByAsset(context, getWebView(), assetName);
    }

    @Override
    public File capture(String absPath) {
        return saveBitmap(getActivity(), absPath, captureWebView(getWebView()));
    }

    @Override
    public List<String> find(String tag) {
        return null;
    }

    @Override
    public void loadUrl(String url) {
        if(getWebView()!=null){
            getWebView().loadUrl(url);
        }
    }

    public interface ProgressCallback{
        void onStart();
        void onFailed();
        void onSuccess();
    }

    public class DefaultWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            isError = false;
            if(callback!=null){
                callback.onStart();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)) {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //取消加载中动画
            //处理加载完成相关事务
            if(callback!=null && !isError){
                callback.onSuccess();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            super.onReceivedError(view, errorCode, description, failingUrl);
            //停止加载动画
            isError = true;
            //显示加载失败页面
            if(callback!=null){
                callback.onFailed();
            }
            //提供加载失败点击后重新刷新
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

    }
}
