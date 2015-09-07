package com.zzy.secret;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Describe 常量配置类
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class Config {
    // public static final String SERVER_URL = "http://demo.eoeschool.com/api/v1/nimings/io";
//public static final String SERVER_URL = "http://192.168.1.101:8080/SecretServer/api.jsp";
    public static final String SERVER_URL = "http://192.168.15.34:8080/SecretServer/api.jsp";

    public static final String KEY_TOKEN = "token";
    public static final String KEY_ACTION = "action";
    public static final String KEY_PHONE_NUM = "phone";
    public static final String KEY_STATUS = "status";
    public static final String KEY_PHONE_MD5 = "phone_md5";
    public static final String KEY_CODE = "code";
    public static final String KEY_CONTACTS = "contacts";
    public static final String KEY_PAGE = "page";
    public static final String KEY_PREPAGE = "perpage";
    public static final String KEY_TIMELINE = "timeline";
    public static final String KEY_MSG_ID = "msgId";
    public static final String KEY_MSG = "msg";
    public static final String KEY_ITEMS = "items";
    public static final String KEY_COMMENTS = "comments";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_PUB_COMMENT = "pub_comment";

    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_FAIL = 0;
    public static final int RESULT_STATUS_INVALID_TOKEN = 2;

    public static final String APP_ID = "com.secret";
    public static final String CHARSET = "utf-8";

    public static final String ACTION_GET_CODE = "send_pass";
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_UPLOAD_CONTACTS = "upload_contacts";
    public static final String ACTION_TIMELINE = "timeline";
    public static final String ACTION_GET_COMMENT = "get_comment";
    public static final String ACTION_PUBLISH = "publish";


    public static final int ACTIVITY_RESULT_NEED_REFRESH = 10000;


    /**
     * sharepreferences获取
     *
     * @param context
     * @param key     KEY_TOKEN  KEY_PHONE_NUM
     * @return
     */

    public static String getCachedValue(Context context, String key) {
        return context.getSharedPreferences(APP_ID, MODE_PRIVATE).getString(key, null);
    }

    /**
     * sharepreferences存储
     *
     * @param context
     * @param key     KEY_TOKEN  KEY_PHONE_NUM
     * @param value
     */

    public static void cacheValue(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();

    }
}
