package com.android.similarwx.base;

import com.android.outbaselibrary.primary.BaseConstants;

/**
 * Created by hanhuailong on 2018/3/26.
 */

public class AppConstants extends BaseConstants {
    public static final int REQUEST_CODE_CAMERA_PERMISSION = 1;

    public static final int REQUEST_CODE_READ_PERMISSION = 2;

    public static final int REQUEST_CODE_WRITE_PERMISSION = 3;

    public static final int REQUEST_CODE_HOMEWORK_SCORE = 101;

    public static final int REQUEST_CODE_HOMEWORK_REMARK = 102;

    public static final int REQUEST_CODE_TAKE_PHOTO = 103;

    public static final int REQUEST_CODE_CHOOSE_IMAGE = 104;

    public static final int REQUEST_CODE_CROP_IMAGE = 105;

    /**
     *servicefragemnt 标签
     */

    /**
     * 聊天通用标签
     */
    public static final String CHAT_CUSTOMIZATION = "customization";
    public static final String CHAT_ACCOUNT_ID = "accountId";
    public static final String CHAT_TYPE = "messageType";
    /**
     * 用户标签
     */
    public static final String USER_ACCID = "accId";
    public static final String USER_TOKEN = "token";

    /**
     * 转账时传递的标示
     */
    public static final String TRANSFER_ACCOUNT = "account";
    /**
     * 更多中选择标示
     */
    public final static int CAPTURE_VIDEO = 1;// 拍摄视频
    public final static int GET_LOCAL_VIDEO = 2;// 选择视频
    public final static int GET_LOCAL_FILE = 3; // 选择文件
    public final static int PICK_IMAGE = 4;
    public final static int PICKER_IMAGE_PREVIEW = 5;
    public final static int PREVIEW_IMAGE_FROM_CAMERA = 6;
    public final static int GET_LOCAL_IMAGE = 7;// 相册
    public final static int SEND_RED_REQUEST = 8;// 相册
}
