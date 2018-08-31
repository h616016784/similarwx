package com.android.similarwx.fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.beans.response.RspConfig;
import com.android.similarwx.fragment.web.JsInterface;
import com.android.similarwx.inteface.SysNoticeViewInterface;
import com.android.similarwx.present.SysNoticePresent;
import com.android.similarwx.utils.CustomTagHandler;
import com.android.similarwx.utils.InputStreamUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/3.
 */

public class ExplainFragment extends BaseFragment implements SysNoticeViewInterface {
    @BindView(R.id.sys_explain_wv)
    WebView sysExplainWv;
    @BindView(R.id.sys_explain_tv)
    TextView sysExplainTv;
    Unbinder unbinder;
    private SysNoticePresent present;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_explain;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        unbinder = ButterKnife.bind(this, contentView);
        mActionbar.setTitle(R.string.explain_title_find);
        init(sysExplainWv);
        present = new SysNoticePresent(this);
        present.getExplain();

    }
    private void init(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

    }
    @Override
    public void onDestroyView() {
        if (sysExplainWv != null) {
            sysExplainWv.clearHistory();

            ((ViewGroup) sysExplainWv.getParent()).removeView(sysExplainWv);
            sysExplainWv.destroy();
            sysExplainWv = null;
        }
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshSysNotice(List<Notice> list) {
        if (list!=null && list.size()>0){
            Notice notice=list.get(0);
            String content=notice.getContent();
            if (!TextUtils.isEmpty(content))
                sysExplainWv.loadData(content,"text/html; charset=UTF-8",null);
//                sysExplainTv.setText(Html.fromHtml(content,null,new CustomTagHandler(activity,null)));
        }

    }

    @Override
    public void refreshSysMoney(String url) {

    }

    @Override
    public void refreshSysConfig(RspConfig.ConfigBean bean) {

    }
}
