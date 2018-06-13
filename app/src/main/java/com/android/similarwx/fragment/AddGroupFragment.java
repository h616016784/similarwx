package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupRule;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.beans.PopMoreBean;
import com.android.similarwx.widget.ListPopWindow;
import com.android.similarwx.widget.dialog.EasyAlertDialogHelper;
import com.android.similarwx.widget.dialog.RuleDialogFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/6/13.
 */

public class AddGroupFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.create_group_name_et)
    EditText createGroupNameEt;
    @BindView(R.id.create_group_set_tv)
    TextView createGroupSetTv;
    @BindView(R.id.create_group_set_rl)
    RelativeLayout createGroupSetRl;
    @BindView(R.id.create_group_notice_et)
    EditText createGroupNoticeEt;
    @BindView(R.id.create_group_must_et)
    EditText createGroupMustEt;
    @BindView(R.id.create_group_home_tv)
    TextView createGroupHomeTv;
    @BindView(R.id.create_group_home_rl)
    RelativeLayout createGroupHomeRl;
    @BindView(R.id.create_group_range_high_et)
    EditText createGroupRangeHighEt;
    @BindView(R.id.create_group_range_low_et)
    EditText createGroupRangeLowEt;
    @BindView(R.id.create_group_num_et)
    EditText createGroupNumEt;
    @BindView(R.id.create_group_lei_et)
    EditText createGroupLeiEt;
    @BindView(R.id.create_group_in_set_tv)
    TextView createGroupInSetTv;
    @BindView(R.id.create_group_in_set_rl)
    RelativeLayout createGroupInSetRl;
    @BindView(R.id.create_group_add_rule_iv)
    ImageView createGroupAddRuleIv;
    @BindView(R.id.create_group_rule_rv)
    RecyclerView createGroupRuleRv;
    @BindView(R.id.create_group_new_bt)
    Button createGroupNewBt;

    private ListPopWindow groupTypePop;
    private List<PopMoreBean> groupTypeList;

    private BaseQuickAdapter adapter;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_add_group;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("创建群组");
        unbinder = ButterKnife.bind(this, contentView);
        initGroupList();
        groupTypePop=new ListPopWindow(activity,groupTypeList);
        groupTypePop.setOnClickItem(new ListPopWindow.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                createGroupSetTv.setText(groupTypeList.get(position).getName());
            }
        });
        createGroupRuleRv.setLayoutManager(new LinearLayoutManager(activity));
        adapter=new BaseQuickAdapter<GroupRule,BaseViewHolder>(R.layout.item_group_info_rule){

            @Override
            protected void convert(BaseViewHolder helper, GroupRule item) {
                helper.setText(R.id.item_create_group_rule_name,item.getName());
                helper.setText(R.id.item_create_group_rule_grab,item.getGrab());
                helper.setText(R.id.item_create_group_rule_get,item.getBack());
            }
        };
        createGroupRuleRv.setAdapter(adapter);
        hideKeyboard();
    }

    private void initGroupList() {
        groupTypeList=new ArrayList<>();
        PopMoreBean bean=new PopMoreBean();
        bean.setId("1");
        bean.setName("游戏群");
        PopMoreBean bean2=new PopMoreBean();
        bean2.setId("2");
        bean2.setName("交友群");
        groupTypeList.add(bean);
        groupTypeList.add(bean2);
    }


    @OnClick({R.id.create_group_set_rl, R.id.create_group_home_rl, R.id.create_group_in_set_rl, R.id.create_group_add_rule_iv, R.id.create_group_new_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_group_set_rl:
                if (groupTypePop!=null){
                    groupTypePop.show(createGroupSetTv);
                }
                break;
            case R.id.create_group_home_rl:
                EasyAlertDialogHelper.createOkCancelDiolag(activity, "提示", "是否大厅显示？","是","否", true, new EasyAlertDialogHelper.OnDialogActionListener() {
                    @Override
                    public void doCancelAction() {
                        createGroupHomeTv.setText("否");
                    }

                    @Override
                    public void doOkAction() {
                        createGroupHomeTv.setText("是");
                    }
                }).show();
                break;
            case R.id.create_group_in_set_rl:
                EasyAlertDialogHelper.createOkCancelDiolag(activity, "提示", "是否进群验证？","是","否", true, new EasyAlertDialogHelper.OnDialogActionListener() {
                    @Override
                    public void doCancelAction() {
                        createGroupInSetTv.setText("否");
                    }

                    @Override
                    public void doOkAction() {
                        createGroupInSetTv.setText("是");
                    }
                }).show();
                break;
            case R.id.create_group_add_rule_iv:
                RuleDialogFragment.show(activity);
                break;
            case R.id.create_group_new_bt:

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
