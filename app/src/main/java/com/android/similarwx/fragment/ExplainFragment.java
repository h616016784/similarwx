package com.android.similarwx.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.beans.response.RspConfig;
import com.android.similarwx.inteface.SysNoticeViewInterface;
import com.android.similarwx.present.SysNoticePresent;
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
    @BindView(R.id.sys_explain_rv)
    RecyclerView sysExplainRv;
    @BindView(R.id.sys_explain_tv)
    TextView sysExplainTv;
    Unbinder unbinder;
    private SysNoticePresent present;
    private BaseQuickAdapter adapter;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_explain;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        unbinder = ButterKnife.bind(this, contentView);
        mActionbar.setTitle(R.string.explain_title_find);
        init();
        present = new SysNoticePresent(this);
        present.getExplain();

    }
    private void init() {
//        sysExplainRv.setLayoutManager(new LinearLayoutManager(activity));
//        adapter=new BaseQuickAdapter<Notice,BaseViewHolder>(R.layout.item_notice,null){
//
//            @Override
//            protected void convert(BaseViewHolder helper, Notice item) {
//                String content=item.getContent();
//                helper.setText(R.id.notice_item_title,item.getTitle());
//            }
//        };
//        sysExplainRv.setAdapter(adapter);
    }
    @Override
    public void onDestroyView() {
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
                sysExplainTv.setText(Html.fromHtml(content));
        }

    }

    @Override
    public void refreshSysMoney(String url) {

    }

    @Override
    public void refreshSysConfig(RspConfig.ConfigBean bean) {

    }

}
