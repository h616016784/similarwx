package com.android.similarwx.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.response.RspInMoney;
import com.android.similarwx.misdk.ScreenUtil;
import com.android.similarwx.utils.BitmapUtil;
import com.android.similarwx.utils.FileUtils;
import com.android.similarwx.utils.QRCodeUtil;
import com.android.similarwx.utils.WXUtil;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/3.
 */

public class PayDetailFragment extends BaseFragment {
    public static final String INMONEY="inMoney";
    public static final String TYPE="type";
    @BindView(R.id.pay_detail_money_tv)
    TextView payDetailMoneyTv;
    @BindView(R.id.pay_detail_iv)
    ImageView payDetailIv;
    @BindView(R.id.pay_detail_share_bt)
    Button payDetailShareBt;
    Unbinder unbinder;

    private RspInMoney.InMoneyBean inMoneyBean;
    private String type="1";

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
            type=  bundle.getString(TYPE);
            if (TextUtils.isEmpty(type)){
                if ("1".equals(type))
                    mActionbar.setTitle(R.string.pay_detail_alipay_title);
                else if ("3".equals(type))
                    mActionbar.setTitle(R.string.pay_detail_title);
            }
            if (inMoneyBean!=null){
                payDetailMoneyTv.setText(inMoneyBean.getMoney());
                String qRcode=inMoneyBean.getQrcode();
                if (!TextUtils.isEmpty(qRcode)){
                    NetImageUtil.glideImageNormal(activity,qRcode,payDetailIv);
//                    Bitmap bitmap=QRCodeUtil.createQRCodeN(qRcode, ScreenUtil.screenWidth/2);
//                    if (bitmap!=null){
//                        payDetailIv.setImageBitmap(bitmap);
//                    }
                }
            }
        }
        payDetailIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Drawable drawable=payDetailIv.getDrawable();
                if (drawable!=null){
                    Bitmap bitmap = BitmapUtil.getBitmap(drawable);
                    File file = FileUtils.createFileInSD("payPic");
                    if (file!=null){
                        BitmapUtil.saveBitmap(bitmap,file);
                        Toaster.toastShort("保存图片成功");
                    }

                }
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({ R.id.pay_detail_share_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.pay_detail_share_bt:
                Drawable drawable=payDetailIv.getDrawable();
                if (drawable!=null){
                    Bitmap bitmap = BitmapUtil.getBitmap(drawable);
                    WXUtil.getInstance(activity).WxShareImage("图片分享", bitmap,SendMessageToWX.Req.WXSceneSession);
                }
                break;
        }
    }
}
