package com.android.similarwx.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseDialog;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.SetPasswordViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.present.SetPasswordPresent;
import com.android.similarwx.utils.DigestUtil;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.Strings.MD5;
import com.android.similarwx.widget.BaseItemView;
import com.android.similarwx.widget.ItemView;
import com.android.similarwx.widget.dialog.AlertDialogFragment;
import com.android.similarwx.widget.dialog.EditDialogBuilder;
import com.android.similarwx.widget.dialog.EditDialogFragment;
import com.android.similarwx.widget.dialog.EditDialogSimple;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/11.
 */

public class SetPasswordFragment extends BaseFragment {
    @BindView(R.id.my_set_password_login_iv)
    BaseItemView mySetPasswordLoginIv;
    @BindView(R.id.my_set_password_pay_iv)
    BaseItemView mySetPasswordPayIv;
    @BindView(R.id.my_set_password_find_iv)
    BaseItemView mySetPasswordFindIv;
    Unbinder unbinder;

    EditDialogSimple editDialogSimple;
    EditDialogSimple editDialogSimplePay;
    User muser;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_set_password;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.my_base_title);
        unbinder = ButterKnife.bind(this, contentView);
        muser= (User) SharePreferenceUtil.getSerializableObjectDefault(activity, AppConstants.USER_OBJECT);
        init();
    }

    private void init() {
        mySetPasswordLoginIv.setNameText(R.string.set_password_login);mySetPasswordLoginIv.setImageView(R.drawable.em_right);
        mySetPasswordPayIv.setNameText(R.string.set_password_pay);mySetPasswordPayIv.setImageView(R.drawable.em_right);
        mySetPasswordFindIv.setNameText(R.string.set_password_find);mySetPasswordFindIv.setImageView(R.drawable.em_right);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (editDialogSimple!=null){
            editDialogSimple.dimiss();
            editDialogSimple=null;
        }
    }

    @OnClick({R.id.my_set_password_login_iv, R.id.my_set_password_pay_iv, R.id.my_set_password_find_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_set_password_login_iv:
                showEditDialog();
                break;
            case R.id.my_set_password_pay_iv:
                showEditDialogPay();
                break;
            case R.id.my_set_password_find_iv:
                Bundle bundle=new Bundle();
                bundle.putString(AppConstants.TRANSFER_PASSWORD_TYPE,SetPayPasswordFragment.PAY_PSD);
                bundle.putInt(SetPayPasswordFragment.MOBILE,1);
                FragmentUtils.navigateToNormalActivity(activity,new PhoneVerifyFragment(),bundle);
                break;
        }
    }

    private void showEditDialogPay() {
        if(editDialogSimplePay==null)
            editDialogSimplePay=new EditDialogSimple(activity,"",1);
        if (TextUtils.isEmpty(muser.getPaymentPasswd())) {
            Bundle bundle=new Bundle();
            bundle.putString(AppConstants.TRANSFER_PASSWORD_TYPE,SetPayPasswordFragment.PAY_PSD);
//                FragmentUtils.navigateToNormalActivity(activity,new SetPayPasswordFragment(),bundle);
            bundle.putInt(SetPayPasswordFragment.MOBILE,0);
            FragmentUtils.navigateToNormalActivity(activity, new SetPayPasswordFragment(), bundle);
        } else {
            editDialogSimplePay.setTitle(AppContext.getResources().getString(R.string.set_password_message));
            editDialogSimplePay.setOnConfirmClickListener(new EditDialogSimple.ConfirmClickListener() {
                @Override
                public void onClickListener(String text) {
                    String myPayPassword = muser.getPaymentPasswd();
                    if (TextUtils.isEmpty(text))
                        Toaster.toastShort("支付密码不能为空");
                    else {
                        if (DigestUtil.sha1(text).equals(myPayPassword)) {
                            Bundle bundle = new Bundle();
                            bundle.putString(AppConstants.TRANSFER_PASSWORD_TYPE, SetPayPasswordFragment.PAY_PSD);
                            bundle.putInt(SetPayPasswordFragment.MOBILE,0);
                            FragmentUtils.navigateToNormalActivity(activity, new SetPayPasswordFragment(), bundle);
                        } else
                            Toaster.toastShort("原支付密码不正确!");
                    }
                }
            });
            editDialogSimplePay.show();
        }
    }

    private void showEditDialog() {
        if(editDialogSimple==null)
            editDialogSimple=new EditDialogSimple(activity,"");
        editDialogSimple.setTitle("请输入原登录密码");
        editDialogSimple.setOnConfirmClickListener(new EditDialogSimple.ConfirmClickListener() {
            @Override
            public void onClickListener(String text) {
                if (TextUtils.isEmpty(text))
                    Toaster.toastShort("登录密码不能为空");
                else if (!DigestUtil.sha1(text).equals(muser.getPasswd()))
                    Toaster.toastShort("登录密码不正确！");
                else {
                    Bundle bundle=new Bundle();
                    bundle.putString(AppConstants.TRANSFER_PASSWORD_TYPE,SetPayPasswordFragment.LOG_PSD);
                    bundle.putInt(SetPayPasswordFragment.MOBILE,0);
                    FragmentUtils.navigateToNormalActivity(activity,new SetPayPasswordFragment(),bundle);
                }
            }
        });
        editDialogSimple.show();
    }
}
