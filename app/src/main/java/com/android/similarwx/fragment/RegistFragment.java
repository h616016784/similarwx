package com.android.similarwx.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.RegisterViewInterface;
import com.android.similarwx.present.RegisterPresent;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/31.
 */

public class RegistFragment extends BaseFragment implements RegisterViewInterface {

    @BindView(R.id.login_phone_et)
    EditText loginPhoneEt;
    @BindView(R.id.register_code_et)
    EditText registerCodeEt;
    @BindView(R.id.register_get_code)
    TextView registerGetCode;
    @BindView(R.id.register_password_et)
    EditText registerPasswordEt;
    @BindView(R.id.register_complete)
    Button registerComplete;
    Unbinder unbinder;
    @BindView(R.id.register_confirm_et)
    EditText registerConfirmEt;
    @BindView(R.id.register_errror)
    TextView register_errror;

    private RegisterPresent registerPresent;
    Timer timer;
    Integer pos=60;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_regist;
    }

    @Override
    protected void initHandler() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0://结束倒计时
                        registerGetCode.setText(AppContext.getContext().getString(R.string.register_get_code));
                        destroyTimer();
                        break;
                    case 1:
                        registerGetCode.setText(pos+"秒");
                        pos--;
                        break;
                }
            }
        };
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        unbinder = ButterKnife.bind(this, contentView);
        mActionbar.setTitle(R.string.login_bar_registe);
        registerPresent = new RegisterPresent(this);
        timer = new Timer();
    }

    @OnClick({R.id.register_get_code, R.id.register_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_get_code:
                String phoneNum=loginPhoneEt.getText().toString();
                if (registerPresent.isEmpty(phoneNum)){ }else {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if(pos==0){
                                mHandler.sendEmptyMessage(0);
                            }else {
                                mHandler.sendEmptyMessage(1);

                            }
                        }
                    },50,1000);
                }
                break;
            case R.id.register_complete:
                String phone = loginPhoneEt.getText().toString();
                String code = registerCodeEt.getText().toString();
                String psd = registerPasswordEt.getText().toString();
                String confirm = registerConfirmEt.getText().toString();
                registerPresent.register(phone,psd,code,confirm);
                break;
        }
    }

    @Override
    public void showErrorMessage(String err) {
        register_errror.setVisibility(View.VISIBLE);
        register_errror.setText(err);
    }

    @Override
    public void loginScucces(User user) {
        register_errror.setVisibility(View.GONE);

    }

    @Override
    public void getCodeScucces(Integer code) {
//        registerCodeEt.setText(code+"");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyTimer();
    }
    public void destroyTimer(){
        if(timer!=null){
            timer.cancel();
            timer=null;
            pos=60;
        }
    }
}
