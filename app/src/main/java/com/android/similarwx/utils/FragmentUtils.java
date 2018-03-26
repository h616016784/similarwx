package com.android.similarwx.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.base.NormalActivity;

public class FragmentUtils {

    private static final String TAG = "FragmentUtils";

    public static final String ARGUMENTS_KEY_NO_BACK_STACK = "noBackStack";

    public static void detachAndAdd(FragmentManager fragmentManager, Fragment fragmentDetach,
                                    Fragment fragmentAdd, Bundle bundle) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentAdd.setArguments(bundle);
        fragmentTransaction.detach(fragmentDetach);
        fragmentTransaction.add(R.id.layout_container_main, fragmentAdd);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    public static void navigateWithAnimations(FragmentManager fragmentManager, Fragment fragment,
                                              Bundle bundle) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        boolean noBackStack = Boolean.FALSE;
        if (bundle != null) {

            if (bundle.getBoolean(ARGUMENTS_KEY_NO_BACK_STACK)) {
                bundle.remove(ARGUMENTS_KEY_NO_BACK_STACK);
                noBackStack = Boolean.TRUE;
            }

            fragment.setArguments(bundle);
        }

        fragmentTransaction.replace(R.id.layout_container_main, fragment);

        if (!noBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

    }

    public static void navigateToNewActivity(Activity activity, Class cls, Fragment fragment,
                                             Bundle bundle) {

        Intent intent = new Intent(activity, cls);

        if (bundle == null) {
            bundle = new Bundle();
        }

        if (bundle.getBoolean(ARGUMENTS_KEY_NO_BACK_STACK)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        }

        intent.putExtra(BaseActivity.ARGUMENT_EXTRA_FRAGMENT_NAME, fragment.getClass().getName());
        intent.putExtras(bundle);

        activity.startActivity(intent);
        activityAnimate(activity, bundle);
    }

    public static void navigateToNormalActivity(Activity activity, Fragment fragment,
                                                Bundle bundle) {

        Intent intent = new Intent(activity, NormalActivity.class);

        if (bundle == null) {
            bundle = new Bundle();
        }

        if (bundle != null && bundle.getBoolean(ARGUMENTS_KEY_NO_BACK_STACK)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        }

        intent.putExtra(BaseActivity.ARGUMENT_EXTRA_FRAGMENT_NAME, fragment.getClass().getName());
        intent.putExtras(bundle);

        activity.startActivity(intent);
        activityAnimate(activity, bundle);
    }

    public static void navigateToNormalActivityForResult(Fragment fragmentContext,
                                                         Fragment fragment, Bundle bundle, int requestCode) {

        Intent intent = new Intent(AppContext.getContext(), NormalActivity.class);
        intent.putExtra(BaseActivity.ARGUMENT_EXTRA_FRAGMENT_NAME, fragment.getClass().getName());
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        fragmentContext.startActivityForResult(intent, requestCode);
        activityAnimate(fragmentContext.getActivity(), bundle);
    }

    public static void activityAnimate(Activity activity, Bundle bundle) {

        boolean noAnimation = false;
        boolean isUpAnim = false;
        boolean isFade = false;
        boolean isCustom = false;

        if (bundle != null) {
            noAnimation = bundle.getBoolean(BaseActivity.ARGUMENT_EXTRA_NO_ANIMATION);
            isUpAnim = bundle.getBoolean(BaseActivity.ARGUMENT_EXTRA_ANIMATION_UP);
            isFade = bundle.getBoolean(BaseActivity.ARGUMENT_EXTRA_ANIMATION_SPLASH);
        }

        if (noAnimation || isCustom) {
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (isUpAnim) {
            activity.overridePendingTransition(R.anim.down_in, R.anim.playlist_slide_out);
        } else if (isFade) {
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else {
            activity.overridePendingTransition(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit);
        }
    }

    public static void removeAndAdd(FragmentManager fragmentManager, Fragment fragmentRemove,
                                    Fragment fragmentAdd, Bundle bundle) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentAdd.setArguments(bundle);
        fragmentTransaction.remove(fragmentRemove);
        fragmentTransaction.add(R.id.layout_container_main, fragmentAdd);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    public static void replaceFragment(int fragmentId, FragmentManager fragmentManager,
                                       Fragment fragment, Bundle bundle) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle arguments = fragment.getArguments();
        if (arguments == null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            fragment.setArguments(bundle);
        } else if (bundle != null) {
            fragment.getArguments().putAll(bundle);
        }

        fragmentTransaction.replace(fragmentId, fragment, fragment.getClass().getName());
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

}
