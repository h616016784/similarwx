package com.android.similarwx.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.beans.SubUser;
import com.android.similarwx.inteface.SubUsersViewInterface;
import com.android.similarwx.present.SubUsersPresent;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/11.
 */

public class PlayerFragment extends BaseFragment implements SubUsersViewInterface {
    @BindView(R.id.my_player_rv)
    RecyclerView myPlayerRv;
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
        present=new SubUsersPresent(this);

        myPlayerRv.setLayoutManager(new LinearLayoutManager(activity));
        adapter=new BaseQuickAdapter<SubUser,BaseViewHolder>(R.layout.item_my_player,null){
            @Override
            protected void convert(BaseViewHolder helper, SubUser item) {
                helper.setText(R.id.item_play_name_tv,item.getUserInfo().getName());
                helper.setText(R.id.item_play_content_tv,item.getUserInfo().getAccId());
                helper.setText(R.id.item_play_num_tv,"("+item.getLevel()+")");
                helper.setText(R.id.item_play_back_tv,"返点总计 ¥ "+item.getReturnAmout());

               ImageView imageView= helper.getView(R.id.item_play_iv);
                NetImageUtil.glideImageCircle(activity,item.getUserInfo().getIcon(),imageView);
            }
        };
        myPlayerRv.setAdapter(adapter);

        present.getSubUsers(null,null,null,null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void refreshSubUsers(List<SubUser> subUsers) {
        if (subUsers!=null){
            adapter.addData(subUsers);
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }
}
