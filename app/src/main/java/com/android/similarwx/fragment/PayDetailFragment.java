package com.android.similarwx.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.response.RspInMoney;
import com.android.similarwx.misdk.ScreenUtil;
import com.android.similarwx.utils.QRCodeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/3.
 */

public class PayDetailFragment extends BaseFragment {
    public static final String INMONEY="inMoney";
    @BindView(R.id.pay_detail_money_tv)
    TextView payDetailMoneyTv;
    @BindView(R.id.pay_detail_iv)
    ImageView payDetailIv;
    @BindView(R.id.pay_detail_share_bt)
    Button payDetailShareBt;
    Unbinder unbinder;

    private RspInMoney.InMoneyBean inMoneyBean;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_pay_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.pay_detail_title);
        unbinder = ButterKnife.bind(this, contentView);
        Bundle bundle=getArguments();
        if (bundle!=null){
            inMoneyBean= (RspInMoney.InMoneyBean) bundle.getSerializable(INMONEY);
            if (inMoneyBean!=null){
                inMoneyBean.getType();
                String qRcode=inMoneyBean.getQrcode();
                if (!TextUtils.isEmpty(qRcode)){
                    Bitmap bitmap=QRCodeUtil.createQRCode(qRcode, ScreenUtil.screenWidth);
                    if (bitmap!=null){
                        payDetailIv.setImageBitmap(bitmap);
                    }
                }
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.pay_detail_iv, R.id.pay_detail_share_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_detail_iv:
                break;
            case R.id.pay_detail_share_bt:
                break;
        }
    }
}
