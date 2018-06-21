package com.android.similarwx.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.fragment.SysNoticeFragment;

/**
 * Created by hanhuailong on 2018/6/21.
 */

public class SysNoticeActivity extends BaseActivity {

    @Override
    protected BaseFragment initContentFragment() {
        BaseFragment fragment=new SysNoticeFragment();
        Bundle bundle=getIntent().getExtras();
        loadFragment(fragment,bundle);
        return fragment;
    }
}
