package com.android.similarwx.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.MyBaseViewInterface;
import com.android.similarwx.misdk.AttachmentStore;
import com.android.similarwx.misdk.Extras;
import com.android.similarwx.misdk.ImageUtil;
import com.android.similarwx.misdk.PickImageHelper;
import com.android.similarwx.misdk.SendImageHelper;
import com.android.similarwx.misdk.StorageType;
import com.android.similarwx.misdk.StorageUtil;
import com.android.similarwx.misdk.activity.PickImageActivity;
import com.android.similarwx.misdk.activity.PreviewImageFromCameraActivity;
import com.android.similarwx.present.MyBasePresent;
import com.android.similarwx.utils.CodeUtils;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.Strings.StringUtil;
import com.android.similarwx.utils.glide.CircleCrop;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.android.similarwx.widget.BaseItemView;
import com.android.similarwx.widget.dialog.TwoButtonDialogBuilder;
import com.bumptech.glide.Glide;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.nos.NosService;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/11.
 */

public class MyBaseFragment extends BaseFragment implements MyBaseViewInterface{
    @BindView(R.id.my_base_head_bv)
    BaseItemView myBaseHeadBv;
    @BindView(R.id.my_base_nikename_bv)
    BaseItemView myBaseNikenameBv;
    @BindView(R.id.my_base_account_bv)
    BaseItemView myBaseAccountBv;
    @BindView(R.id.my_base_sex_bv)
    BaseItemView myBaseSexBv;
    @BindView(R.id.my_base_sign_bv)
    BaseItemView myBaseSignBv;
    @BindView(R.id.view_base_iv)
    ImageView viewBaseIv;
    Unbinder unbinder;


    private static final int PICK_IMAGE_COUNT = 9;
    private static final int PORTRAIT_IMAGE_WIDTH = 720;

    public static final String MIME_JPEG = "image/jpeg";
    public static final String JPG = ".jpg";

    private boolean multiSelect;
    private boolean crop = false;

    private User mUser;
    private MyBasePresent myBasePresent;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_base;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.my_base_title);
        unbinder = ButterKnife.bind(this, contentView);
        init();
    }

    private void init() {
        myBasePresent=new MyBasePresent(this,activity);
        myBaseHeadBv.setNameText(R.string.my_base_head);
        myBaseHeadBv.setImageView(R.drawable.rp_avatar);
        myBaseNikenameBv.setNameText(R.string.my_base_nick);
        myBaseNikenameBv.setImageView(R.drawable.em_right);
        myBaseAccountBv.setNameText(R.string.my_base_account);
        myBaseSexBv.setNameText(R.string.my_base_sex);
        myBaseSexBv.setImageView(R.drawable.em_right);
        myBaseSignBv.setNameText(R.string.my_base_sign);
        myBaseSignBv.setImageView(R.drawable.em_right);
    }

    @Override
    public void onResume() {
        super.onResume();
        mUser= (User) SharePreferenceUtil.getSerializableObjectDefault(activity, AppConstants.USER_OBJECT);
        if (mUser!=null){
            myBaseNikenameBv.setRightText(mUser.getName());
            myBaseAccountBv.setRightText(mUser.getAccId());
            //性别
            String gender=mUser.getGender();
            if (TextUtils.isEmpty(gender)){
                myBaseSexBv.setRightText("未知");
            }else {
                if ("0".equals(gender))
                    myBaseSexBv.setRightText("男");
                else if ("1".equals(gender))
                    myBaseSexBv.setRightText("女");
                else
                    myBaseSexBv.setRightText("未知");
            }
            myBaseSignBv.setRightText(mUser.getPersonalitySignature()==null?"":mUser.getPersonalitySignature());
            //头像
            String icon= mUser.getIcon();

            if (!TextUtils.isEmpty(icon)){
                NetImageUtil.glideImageNormalWithSize(activity,icon,viewBaseIv,240,240);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_base_head_bv, R.id.my_base_nikename_bv, R.id.my_base_sex_bv, R.id.my_base_sign_bv})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.my_base_head_bv:
                showHeadDialog();
                break;
            case R.id.my_base_nikename_bv:
                bundle=new Bundle();
                bundle.putString(AppConstants.TRANSFER_BASE,AppConstants.USER_NICK);
//                FragmentUtils.navigateToNormalActivity(activity,new EditFragment(),bundle);
                FragmentUtils.navigateToNormalActivityForResult(this,new EditFragment(),bundle,AppConstants.RESULT_USER_NICK);
                break;
            case R.id.my_base_sex_bv:
                showSexDialog();
                break;
            case R.id.my_base_sign_bv:
                bundle=new Bundle();
                bundle.putString(AppConstants.TRANSFER_BASE,AppConstants.USER_SIGN);
//                FragmentUtils.navigateToNormalActivity(activity,new EditFragment(),bundle);
                FragmentUtils.navigateToNormalActivityForResult(this,new EditFragment(),bundle,AppConstants.RESULT_USER_SIGN);
                break;
        }
    }

    private void showSexDialog() {
        Dialog dialog= new TwoButtonDialogBuilder(activity)
                .setMessage("选择性别")
                .setButtonText(R.string.my_base_man,R.string.my_base_women)
                .setConfirmButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myBasePresent.updateUserByGender("0");
                    }
                })
                .setCancelButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myBasePresent.updateUserByGender("1");

                    }
                })
                .setCancleBackground(R.drawable.green_round_rect_btn)
                .create();
        dialog.show();
    }

    /**
     * 以下是图片选择框架=============================================================
     */
    private void showHeadDialog() {
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.titleResId = R.string.input_panel_photo;
        option.multiSelect = multiSelect;
        option.multiSelectMaxCount = PICK_IMAGE_COUNT;
        option.crop = crop;
        option.cropOutputImageWidth = PORTRAIT_IMAGE_WIDTH;
        option.cropOutputImageHeight = PORTRAIT_IMAGE_WIDTH;
        option.outputPath = tempFile();

        PickImageHelper.pickImage(activity, AppConstants.PICK_IMAGE, option);
    }
    private String tempFile() {
        String filename = StringUtil.get32UUID() + JPG;
        return StorageUtil.getWritePath(filename, StorageType.TYPE_TEMP);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppConstants.RESULT_USER_NICK:
                if (data!=null){
                    String content=data.getStringExtra(AppConstants.USER_CONTENT);
                    if (!TextUtils.isEmpty(content)){
                        myBasePresent.updateUserByNick(content);
                    }
                }
                break;
            case AppConstants.RESULT_USER_SIGN:
                if (data!=null){
                    String content=data.getStringExtra(AppConstants.USER_CONTENT);
                    if (!TextUtils.isEmpty(content)){
//                        content= CodeUtils.convertUTF8ToString(content);
                        myBasePresent.updateUserBySign(content);
                    }
                }
                break;
            case AppConstants.PICK_IMAGE:
                onPickImageActivityResult(requestCode, data);
                break;
            case AppConstants.PREVIEW_IMAGE_FROM_CAMERA:
                onPreviewImageActivityResult(requestCode, data);
                break;
        }
    }
    /**
     * 图片选取回调
     */
    private void onPickImageActivityResult(int requestCode, Intent data) {
        if (data == null) {
            Toast.makeText(getActivity(), R.string.picker_image_error, Toast.LENGTH_LONG).show();
            return;
        }
        boolean local = data.getBooleanExtra(Extras.EXTRA_FROM_LOCAL, false);
        if (local) {
            // 本地相册
            sendImageAfterSelfImagePicker(data);
        } else {
            // 拍照
            Intent intent = new Intent();
            if (!handleImagePath(intent, data)) {
                return;
            }
            intent.setClass(getActivity(), PreviewImageFromCameraActivity.class);
            getActivity().startActivityForResult(intent, AppConstants.PREVIEW_IMAGE_FROM_CAMERA);
        }
    }
    /**
     * 是否可以获取图片
     */
    private boolean handleImagePath(Intent intent, Intent data) {
        String photoPath = data.getStringExtra(Extras.EXTRA_FILE_PATH);
        if (TextUtils.isEmpty(photoPath)) {
            Toast.makeText(getActivity(), R.string.picker_image_error, Toast.LENGTH_LONG).show();
            return false;
        }

        File imageFile = new File(photoPath);
        intent.putExtra("OrigImageFilePath", photoPath);
        File scaledImageFile = ImageUtil.getScaledImageFileWithMD5(imageFile, MIME_JPEG);

        boolean local = data.getExtras().getBoolean(Extras.EXTRA_FROM_LOCAL, true);
        if (!local) {
            // 删除拍照生成的临时文件
            AttachmentStore.delete(photoPath);
        }

        if (scaledImageFile == null) {
            Toast.makeText(getActivity(), R.string.picker_image_error, Toast.LENGTH_LONG).show();
            return false;
        } else {
            ImageUtil.makeThumbnail(getActivity(), scaledImageFile);
        }
        intent.putExtra("ImageFilePath", scaledImageFile.getAbsolutePath());
        return true;
    }
    /**
     * 拍摄回调
     */
    private void onPreviewImageActivityResult(int requestCode, Intent data) {
        if (data.getBooleanExtra(PreviewImageFromCameraActivity.RESULT_SEND, false)) {
            sendImageAfterPreviewPhotoActivityResult(data);
        } else if (data.getBooleanExtra(PreviewImageFromCameraActivity.RESULT_RETAKE, false)) {
            String filename = StringUtil.get32UUID() + JPG;
            String path = StorageUtil.getWritePath(filename, StorageType.TYPE_TEMP);

            if (requestCode == AppConstants.PREVIEW_IMAGE_FROM_CAMERA) {
                PickImageActivity.start(getActivity(), AppConstants.PICK_IMAGE, PickImageActivity.FROM_CAMERA, path);
            }
        }
    }
    /**
     * 从预览界面点击发送图片
     */
    private void sendImageAfterPreviewPhotoActivityResult(Intent data) {
        SendImageHelper.sendImageAfterPreviewPhotoActivityResult(data, new SendImageHelper.Callback() {

            @Override
            public void sendImage(File file, boolean isOrig) {
//                onPicked(file);
                Log.e("从预览界面点击发送图片",file.getAbsolutePath());
                upLoadImage(file);
            }
        });
    }


    /**
     * 发送图片
     */
    private void sendImageAfterSelfImagePicker(final Intent data) {
        SendImageHelper.sendImageAfterSelfImagePicker(getActivity(), data, new SendImageHelper.Callback() {

            @Override
            public void sendImage(File file, boolean isOrig) {
                Log.e("发送图片",file.getAbsolutePath());
                upLoadImage(file);
            }
        });
    }

    private void upLoadImage(File file) {
        if (file!=null){
            NIMClient.getService(NosService.class).upload(file,"headImage").setCallback(new RequestCallback<String>() {
                @Override
                public void onSuccess(String param) {
                    Log.e("onSuccess",param);
                    if (!TextUtils.isEmpty(param))
                        myBasePresent.updateUserByUrl(param);
                }

                @Override
                public void onFailed(int code) {
                    Toaster.toastShort(code+"");
                }

                @Override
                public void onException(Throwable exception) {
                    Toaster.toastShort(exception.toString());
                }
            });
        }
    }
    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void reFreshUser(User user) {
        if (user!=null){
            myBaseNikenameBv.setRightText(user.getName());
            myBaseSignBv.setRightText(user.getPersonalitySignature());
            String gender=user.getGender();
            if (TextUtils.isEmpty(gender)){
                myBaseSexBv.setRightText("未知");
            }else {
                if ("0".equals(gender))
                    myBaseSexBv.setRightText("男");
                else if ("1".equals(gender))
                    myBaseSexBv.setRightText("女");
                else
                    myBaseSexBv.setRightText("未知");
            }
            String icon=user.getIcon();
            if (!TextUtils.isEmpty(icon)){
                Glide.with(activity)
                        .load(icon)
//                        .override(120,120)
//                        .transform(new CircleCrop(activity))
//                        .placeholder(R.drawable.rp_avatar)
//                        .error(R.drawable.rp_avatar)
                        .into(myBaseHeadBv.getRightImageView());
            }
        }
    }
}
