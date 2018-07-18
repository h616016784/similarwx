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
    public static final String CHAT_ACCOUNT_NAME = "accountName";
    public static final String CHAT_TYPE = "messageType";
    public static final String CHAT_GROUP_BEAN = "groupBean";

    /**
     * 用户标签
     */

    public static final String USER_ID = "id";
    public static final String USER_KEY = "useKey";
    public static final String USER_NONCE = "nonce";
    public static final String USER_TIME = "curTime";
    public static final String USER_CHECK_SUM = "checkSum";

    /**
     * 转账时传递的标示
     */
    public static final String TRANSFER_ACCOUNT = "account";
    public static final String TRANSFER_AWARDRULE = "rewardRule";
    public static final String TRANSFER_BASE = "tag";
    public static final String TRANSFER_CHAT_REDENTITY= "redDetail";
    public static final String TRANSFER_CHAT_TRANS= "tran";
    public static final String TRANSFER_ISHOST= "isHost";
    public static final String TRANSFER_GROUP_USER_ROLE= "groupUserRole";
    public static final String TRANSFER_PASSWORD_TYPE= "passwordType";
    public static final String TRANSFER_VERCODE= "vercode";
    public static final String TRANSFER_BILL_BEAN= "billBean";
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
    public final static int SEND_RED_REQUEST = 8;// 红包 
    public final static int SEND_TRAN_REQUEST = 9;// 转账

    /**
     * 用户基本信息
     */
    public static final String USER_OBJECT = "userObj";
    public static final String USER_ACCID = "accId";
    public static final String USER_TOKEN = "token";
    public static final String USER_EMAIL= "email";
    public static final String USER_PHONE= "phone";
    public final static String USER_NICK = "nick";//
    public final static String USER_WEIXIN= "weixin";//
    public final static String USER_SEX= "sex";//
    public final static String USER_SIGN = "sign";//
    public final static String USER_CONTENT = "content";//
    public final static String USER_PAYPASSWORD = "payPassword";//
    public final static String USER_LOGIN_PASSWORD = "loginPassword";//
    public final static int RESULT_USER_NICK = 9;//
    public final static int RESULT_USER_SIGN = 10;//


    /**
     * 微信
     */
    public static final String WX_APP_ID="wxef17710e91048981";
}
