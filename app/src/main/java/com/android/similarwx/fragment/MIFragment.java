package com.android.similarwx.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.adapter.BaseDecoration;
import com.android.similarwx.adapter.MIMultiItemQuickAdapter;
import com.android.similarwx.adapter.MultipleItemQuickAdapter;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.MIMultiItem;
import com.android.similarwx.beans.MultipleItem;
import com.android.similarwx.widget.input.InputPanel;
import com.android.similarwx.widget.input.actions.BaseAction;
import com.android.similarwx.widget.input.actions.ImageAction;
import com.android.similarwx.widget.input.actions.LocationAction;
import com.android.similarwx.widget.input.actions.VideoAction;
import com.android.similarwx.widget.input.module.Container;
import com.android.similarwx.widget.input.module.ModuleProxy;
import com.android.similarwx.widget.input.sessions.Extras;
import com.android.similarwx.widget.input.sessions.SessionCustomization;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/3/28.
 */

public class MIFragment extends BaseFragment implements ModuleProxy {
    List<MIMultiItem> list;
    @BindView(R.id.mi_recyclerView)
    RecyclerView miRecyclerView;
    Unbinder unbinder;

    private MultipleItemQuickAdapter multipleItemAdapter;
    private List<MultipleItem> data;
    protected InputPanel inputPanel;
    private SessionCustomization customization;
    // 聊天对象
    protected String sessionId; // p2p对方Account或者群id
    protected SessionTypeEnum sessionType;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mi_layout;
    }

    @Override
    protected void onInitView(View contentView) {
        unbinder=ButterKnife.bind(this, contentView);
        mActionbar.setRightImage(R.drawable.action_right_delete);
        mActionbar.setRightImagePeople(R.drawable.action_right_people);
        mActionbar.setRightImagePeopleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        customization = (SessionCustomization) getArguments().getSerializable(Extras.EXTRA_CUSTOMIZATION);
        sessionId = getArguments().getString(Extras.EXTRA_ACCOUNT);
        sessionType = (SessionTypeEnum) getArguments().getSerializable(Extras.EXTRA_TYPE);
        View rootView=contentView.findViewById(R.id.messageActivityBottomLayout);
        Container container = new Container(activity, sessionId, sessionType, this);
        if (inputPanel == null) {
            inputPanel = new InputPanel(container, rootView, getActionList());
//            inputPanel.setCustomization(customization);
        } else {
            inputPanel.reload(container, customization);
        }
        multipleItemAdapter = new MultipleItemQuickAdapter(activity,null);
        multipleItemAdapter.setUpFetchEnable(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        miRecyclerView.setLayoutManager(linearLayoutManager);
        miRecyclerView.setAdapter(multipleItemAdapter);
        multipleItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toaster.toastShort("点击了"+position+"...");
            }
        });

        multipleItemAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                multipleItemAdapter.setUpFetching(true);
                Toaster.toastShort("下拉加载了！");
                multipleItemAdapter.setUpFetching(false);
            }
        });
    }

    private List<MultipleItem> initData() {
        List<MultipleItem> list = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
            list.add(new MultipleItem(MultipleItem.TEXT, MultipleItem.TEXT_SPAN_SIZE, "CymChad"));
            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE));
            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
        }
        return list;
    }

    @Override
    protected void fetchData() {
        data= initData();
        multipleItemAdapter.addData(data);
    }

    /**
     * 如果需要创建fragment就加载数据的话 此方法返回true，就会调用fetchData方法
     *
     * @return
     */
    @Override
    protected boolean isNeedFetch() {
        return true;
    }

    // 操作面板集合
    protected List<BaseAction> getActionList() {
        List<BaseAction> actions = new ArrayList<>();
        actions.add(new ImageAction());
        actions.add(new VideoAction());
        actions.add(new LocationAction());

        if (customization != null && customization.actions != null) {
            actions.addAll(customization.actions);
        }
        return actions;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
/*   ==========================================================*/
    /**
     * 以下是实现了ModuleProxy接口的方法
     * @param msg
     * @return
     */
    @Override
    public boolean sendMessage(IMMessage msg) {
        return false;
    }

    @Override
    public void onInputPanelExpand() {

    }

    @Override
    public void shouldCollapseInputPanel() {

    }

    @Override
    public boolean isLongClickEnabled() {
        return false;
    }

    @Override
    public void onItemFooterClick(IMMessage message) {

    }
}
