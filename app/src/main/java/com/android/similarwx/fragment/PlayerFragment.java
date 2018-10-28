package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.SubUser;
import com.android.similarwx.inteface.SubUsersViewInterface;
import com.android.similarwx.present.SubUsersPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/11.
 */

public class PlayerFragment extends BaseFragment implements SubUsersViewInterface {
    @BindView(R.id.my_player_rv)
    RecyclerView myPlayerRv;
    @BindView(R.id.player_iv)
    ImageView myPlayerIv;
    @BindView(R.id.player_et)
    EditText myPlayerEt;
    Unbinder unbinder;

    private BaseQuickAdapter adapter;
    private SubUsersPresent present;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_player;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.player_title);
        unbinder = ButterKnife.bind(this, contentView);
        present = new SubUsersPresent(this,activity);

        myPlayerRv.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new BaseQuickAdapter<SubUser, BaseViewHolder>(R.layout.item_my_player, null) {
            @Override
            protected void convert(BaseViewHolder helper, SubUser item) {
                helper.setText(R.id.item_play_name_tv, item.getUserInfo().getName());
                helper.setText(R.id.item_play_content_tv, item.getUserInfo().getId());
                helper.setText(R.id.item_play_num_tv, "(" + item.getLevel() + ")");
                String returnAmount=item.getReturnAmout();
                if (TextUtils.isEmpty(returnAmount))
                    returnAmount="0";
                helper.setText(R.id.item_play_back_tv, "返点总计 ¥ " + returnAmount);
                ImageView imageView = helper.getView(R.id.item_play_iv);
                NetImageUtil.glideImageCircle(activity, item.getUserInfo().getIcon(), imageView);
            }
        };
        myPlayerRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SubUser item= (SubUser) adapter.getData().get(position);
                Bundle bundle=new Bundle();
                bundle.putString(AppConstants.TRANSFER_ACCOUNT,item.getUserId());
                bundle.putString(AppConstants.TRANSFER_BASE,item.getUserInfo().getName());
                FragmentUtils.navigateToNormalActivity(getActivity(),new MyDetailFragment(),bundle);
            }
        });
        present.getSubUsers(null, null, null, null);
    }


    @OnClick(R.id.player_iv)
    public void onViewClicked() {
        String content = myPlayerEt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toaster.toastShort("搜索内容不能为空！");
            return;
        }
        present.getSubUsers(null, content, null, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void refreshSubUsers(List<SubUser> subUsers) {
        adapter.getData().clear();
        if (subUsers != null) {
            adapter.addData(subUsers);
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }

}
