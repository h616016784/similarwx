package com.android.similarwx.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.misdk.AttachmentStore;
import com.android.similarwx.misdk.Extras;
import com.android.similarwx.misdk.ImageUtil;
import com.android.similarwx.misdk.PickImageHelper;
import com.android.similarwx.misdk.SendImageHelper;
import com.android.similarwx.misdk.StorageType;
import com.android.similarwx.misdk.StorageUtil;
import com.android.similarwx.misdk.activity.PickImageActivity;
import com.android.similarwx.misdk.activity.PreviewImageFromCameraActivity;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.Strings.StringUtil;
import com.android.similarwx.widget.BaseItemView;
import com.android.similarwx.widget.dialog.TwoButtonDialogBuilder;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/11.
 */

public class MyBaseFragment extends BaseFragment {
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
    Unbinder unbinder;


    private static final int PICK_IMAGE_COUNT = 9;
    private static final int PORTRAIT_IMAGE_WIDTH = 720;

    public static final String MIME_JPEG = "image/jpeg";
    public static final String JPG = ".jpg";

    private boolean multiSelect;
    private boolean crop = false;
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
        myBaseHeadBv.setNameText(R.string.my_base_head);myBaseHeadBv.setImageView(R.drawable.rp_avatar);
        myBaseNikenameBv.setNameText(R.string.my_base_nick);
        myBaseAccountBv.setNameText(R.string.my_base_account);
        myBaseSexBv.setNameText(R.string.my_base_sex);
        myBaseSignBv.setNameText(R.string.my_base_sign);
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
                        myBaseSexBv.setRightText("男");
                    }
                })
                .setCancelButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myBaseSexBv.setRightText("女");
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
                    myBaseNikenameBv.setRightText(content);
                }
                break;
            case AppConstants.RESULT_USER_SIGN:
                if (data!=null){
                    String content=data.getStringExtra(AppConstants.USER_CONTENT);
                    myBaseSignBv.setRightText(content);
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
            }
        });
    }
}
