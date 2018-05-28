package com.android.similarwx.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.adapter.HomeAdapter;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.fragment.ChartFragment;
import com.android.similarwx.fragment.ExplainFragment;
import com.android.similarwx.fragment.MIFragment;
import com.android.similarwx.fragment.MIFragmentNew;
import com.android.similarwx.fragment.MyFragment;
import com.android.similarwx.fragment.NoticeFragment;
import com.android.similarwx.fragment.ServiceFragment;
import com.android.similarwx.inteface.MainGroupView;
import com.android.similarwx.present.GroupPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.widget.dialog.EditDialogSimple;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/1.
 */

public class MainChartrActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, MainGroupView {

    Unbinder unbinder;
    @BindView(R.id.main_search_iv)
    ImageView mainSearchIv;
    @BindView(R.id.create_group_iv)
    ImageView createGroupIv;
    @BindView(R.id.main_search_et)
    EditText mainSearchEt;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.main_rl_explain)
    RelativeLayout mainRlExplain;
    @BindView(R.id.main_rl_chart)
    RelativeLayout mainRlChart;
    @BindView(R.id.main_my_chart)
    RelativeLayout mainMyChart;

    private HomeAdapter adapter;
    private List<GroupMessageBean.ListBean> mListData;
    GroupPresent groupPresent;
    private EditDialogSimple editDialogSimple;

    public static void start(Activity context) {
        Intent intent = new Intent(context, MainChartrActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian_lt);
        unbinder = ButterKnife.bind(this);
        createGroupIv.setVisibility(View.GONE);
        groupPresent = new GroupPresent(this);
        editDialogSimple = new EditDialogSimple(this, null);
//        initData();
        groupPresent.getGroupList();
        adapter = new HomeAdapter(R.layout.item_group, this, mListData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.requestFocus();
        adapter.setOnItemClickListener(this);
        hideKeyboard();
    }

    private void initData() {
        mListData = groupPresent.getInitData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.main_search_iv, R.id.main_rl_explain, R.id.main_rl_chart, R.id.main_my_chart,R.id.create_group_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_search_iv:

                break;
            case R.id.create_group_iv:

                break;
            case R.id.main_rl_explain:
                FragmentUtils.navigateToNormalActivity(this, new ExplainFragment(), null);
                break;
            case R.id.main_rl_chart:
                Bundle bundle = new Bundle();
                bundle.putInt(MIFragment.MIFLAG, MIFragment.DELETE_THREE);
                FragmentUtils.navigateToNormalActivity(this, new ChartFragment(), bundle);
//                FragmentUtils.navigateToNormalActivity(this,new MIFragment(),bundle);
                break;
            case R.id.main_my_chart:
                FragmentUtils.navigateToNormalActivity(this, new MyFragment(), null);
                break;
        }
    }

    /**
     * 点击群列表回调函数
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
        if (position == 0) {//通知
            FragmentUtils.navigateToNormalActivity(this, new NoticeFragment(), null);
        } else if (position == 1) {//在线客服
            FragmentUtils.navigateToNormalActivity(this, new ServiceFragment(), null);
        } else {
            editDialogSimple.setTitle(mListData.get(position).getGroupName());
            editDialogSimple.show();
            editDialogSimple.setOnConfirmClickListener(new EditDialogSimple.ConfirmClickListener() {
                @Override
                public void onClickListener(String text) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(MIFragment.MIFLAG, MIFragment.DELETE_GROUP_EIGHT);
                    bundle.putSerializable(AppConstants.CHAT_TYPE, SessionTypeEnum.Team);
                    bundle.putString(AppConstants.CHAT_ACCOUNT_ID, mListData.get(position).getGroupId());//群id号
                    bundle.putString(AppConstants.CHAT_ACCOUNT_NAME, mListData.get(position).getGroupName());//群name
                    FragmentUtils.navigateToNormalActivity(MainChartrActivity.this, new MIFragmentNew(), bundle);
                }
            });
        }
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
        if (imm.isActive()) {//如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void groupRefresh(List<GroupMessageBean.ListBean> data) {
        mListData = new ArrayList<>();
        GroupMessageBean.ListBean bean = new GroupMessageBean.ListBean();
        bean.setGroupName(AppContext.getString(R.string.main_notice));
        mListData.add(bean);
        GroupMessageBean.ListBean bean1 = new GroupMessageBean.ListBean();
        bean1.setGroupName(AppContext.getString(R.string.main_online_answer));
        mListData.add(bean1);
        mListData.addAll(data);
        adapter.addData(mListData);
    }
}
