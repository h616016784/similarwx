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

import com.android.outbaselibrary.primary.Log;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupRule;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.beans.response.RspConfig;
import com.android.similarwx.inteface.SysNoticeViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.present.SysNoticePresent;
import com.android.similarwx.utils.InputStreamUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.model.SystemMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/10.
 */

public class SysNoticeFragment extends BaseFragment implements SysNoticeViewInterface {

    @BindView(R.id.sys_notice_rv)
    RecyclerView sysNoticeRv;
    Unbinder unbinder;
    private BaseQuickAdapter adapter;
    private SysNoticePresent present;
    private String tag;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sys_notice;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        Bundle bundle=getArguments();
        if (bundle!=null)
            tag=bundle.getString(AppConstants.TRANSFER_BASE);
        if (TextUtils.isEmpty(tag))
            mActionbar.setTitle(R.string.sys_notice_title);
        else if(tag.equals("contract")){
            mActionbar.setTitle("联系群主");
        }else
            mActionbar.setTitle(R.string.sys_notice_title);
        unbinder = ButterKnife.bind(this, contentView);
        present=new SysNoticePresent(this,activity);
        init();
    }

    private void init() {
        sysNoticeRv.setLayoutManager(new LinearLayoutManager(activity));
        adapter=new BaseQuickAdapter<Notice,BaseViewHolder>(R.layout.item_notice,null){

            @Override
            protected void convert(BaseViewHolder helper, Notice item) {
                String content=item.getContent();
                helper.setText(R.id.notice_item_title,item.getTitle());
                helper.setText(R.id.notice_item_time,item.getModifyDate());
                helper.setText(R.id.notice_item_content,item.getRemark());
//                if (!TextUtils.isEmpty(content)){
//                    String text=Html.fromHtml(content).toString();
//                    helper.setText(R.id.notice_item_content_detail,text);
//                }
                if (!TextUtils.isEmpty(content)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Spanned sp=Html.fromHtml(content, new Html.ImageGetter() {
                                @Override
                                public Drawable getDrawable(String source) {

                                    Drawable drawable = null;
                                    OkHttpClient okHttpClient = new OkHttpClient();
                                    Request request = new Request.Builder()
                                            .url(source)
                                            .build();

                                    Call call = okHttpClient.newCall(request);
                                    try {
                                        Response response=call.execute();
                                        //得到从网上获取资源，转换成我们想要的类型
                                        byte[] Picture_bt = response.body().bytes();
                                        InputStream ins= null;
                                        try {
                                            ins = InputStreamUtil.byteTOInputStream(Picture_bt);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            return null;
                                        }
                                        drawable=Drawable.createFromStream(ins,"");

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                    if (drawable!=null)
                                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                                    return drawable;
                                }
                            },null);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    helper.setText(R.id.notice_item_content_detail,sp);
                                }
                            });
                        }
                    }).start();


                }
            }
        };
        sysNoticeRv.setAdapter(adapter);

        if (TextUtils.isEmpty(tag))
            present.getNotice();
        else if(tag.equals("contract"))
            present.getContract();
        else
            present.getNotice();
    }

    @Override
    protected void fetchData() {
        super.fetchData();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected boolean isNeedFetch() {
        return true;
    }

    @Override
    public void refreshSysNotice(List<Notice> list) {
        if (list!=null){
            adapter.addData(list);
        }
    }

    @Override
    public void refreshSysMoney(String url) {

    }

    @Override
    public void refreshSysConfig(RspConfig.ConfigBean bean) {

    }

    @Override
    public void showErrorMessage(String err) {

    }
}
