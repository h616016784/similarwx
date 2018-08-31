package com.android.similarwx.fragment.web;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by puyafeng on 2017/7/6.
 */

public class JsInterface {
    public static final String NAME = "android";
    public static final String JS_BASE = "javascript:(%s)()";
    public static final String JS_GET_HTML = "function(){alert(document.getElementsByTagName('html')[0].innerHTML)) ;}";
    public static final String JS_GET_RETURN = "javascript:(\n" +
            "(\n" +
            "function()\n" +
            "{\n" +
            "window.android.value(\n" +
            "function(){\n" +
            "var rd = ((%s)());\n" +
            "if(typeof rd == 'object' || Array.isArray(rd)){\n" +
            "\treturn JSON.stringify(rd);\n" +
            "}else{\n" +
            "\treturn rd;\n" +
            "}\n" +
            "}()\n" +
            ")\n" +
            "}\n" +
            ")()\n" +
            ")()";
    private OnCallJs callJsListener;

    public static void callJs(WebView wv, String js) {
        if (wv != null) {
            String url = String.format(JsInterface.JS_GET_RETURN, js);
            Log.e("js", url);
            wv.loadUrl(url);
        }
    }

    public static void callJs(WebView wv, String stringHtml, String js) {
        if (wv != null) {
            String url = String.format(JsInterface.JS_GET_RETURN, js);
            Log.e("js", url);
            wv.loadUrl(url);
            wv.loadDataWithBaseURL("file:///android_asset", stringHtml, "text/html", "utf-8", null);
        }
    }

    /**
     * 将js方法配置到asset下文件中
     *
     * @param context
     * @param wv
     * @param assetName
     */
    public static void callJsByAsset(Context context, WebView wv, String assetName) {
        callJs(wv, getStringFromAssets(context, assetName));
    }

    public static String getStringFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setCallJsListener(OnCallJs listener) {
        this.callJsListener = listener;
    }

    @JavascriptInterface
    public String value(String jsReturnValue) {
        if (callJsListener != null) {
            callJsListener.call(jsReturnValue);
        }
        return jsReturnValue;
    }

    public interface OnCallJs {
        void call(String result);
    }


}
