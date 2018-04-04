package com.android.similarwx.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

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
import com.netease.nim.uikit.business.robot.parser.elements.group.LinearLayout;
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
    @BindView(R.id.mi_button)
    Button miButton;
    @BindView(R.id.mi_remove)
    Button miRemove;

    private MIMultiItemQuickAdapter adapter;

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
        ButterKnife.bind(this, contentView);
        final List<MultipleItem> data = initData();

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
//        adapter = new BaseAdapter(R.layout.item_test,data);
//        adapter=new MIMultiItemQuickAdapter(data);
//        miRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
//        miRecyclerView.setLayoutManager(new GridLayoutManager(activity,4));
//        miRecyclerView.addItemDecoration(new BaseDecoration());
//        miRecyclerView.setLayoutManager(new GridLayoutManager(activity,3));
//        miRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final MultipleItemQuickAdapter multipleItemAdapter = new MultipleItemQuickAdapter(data);
        final GridLayoutManager manager = new GridLayoutManager(activity, 4);
        miRecyclerView.setLayoutManager(manager);
//        multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
//                return 3;
//            }
//        });
        miRecyclerView.setAdapter(multipleItemAdapter);
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                Toaster.toastShort("点击了"+position+"..."+((TextView)view).getText().toString());
//            }
//        });
//        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
//        adapter.setUpFetchEnable(true);
//        adapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
//            @Override
//            public void onUpFetch() {
//                adapter.setUpFetching(true);
//                Toaster.toastShort("下拉加载了！");
//                adapter.setUpFetching(false);
//            }
//        });
    }

//    private List<String> initData() {
//        list = new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        list.add("e");
//        list.add("f");
//        list.add("h");
//        list.add("i");
//        list.add("g");
//        list.add("k");
//        list.add("l");
//        list.add("m");
//        list.add("n");
//        return list;
//    }
    private List<MultipleItem> initData() {
//        list = new ArrayList<>();
//        list.add(new MIMultiItem(1));
//        list.add(new MIMultiItem(0));
//        list.add(new MIMultiItem(0));
//        list.add(new MIMultiItem(1));
//        list.add(new MIMultiItem(1));
//        list.add(new MIMultiItem(1));
//        list.add(new MIMultiItem(0));
//        list.add(new MIMultiItem(1));
//        list.add(new MIMultiItem(0));
//        list.add(new MIMultiItem(1));
//        list.add(new MIMultiItem(1));
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

    @OnClick({R.id.mi_button, R.id.mi_remove})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mi_button:
//                adapter.addData("abc");

                break;
            case R.id.mi_remove:

                break;
        }
    }
    @Override
    protected void fetchData() {
        super.fetchData();
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
