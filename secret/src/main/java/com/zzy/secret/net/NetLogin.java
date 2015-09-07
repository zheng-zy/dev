package com.zzy.secret.net;

import com.zzy.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Describe 登陆通信
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class NetLogin {

    public NetLogin(String phone_md5, String code, final SuccessCallBack successCallBack, final FailCallBack failCallBack) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallBack() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack!=null){
                                successCallBack.onSuccess(jsonObject.getString(Config.KEY_TOKEN));
                            }
                            break;

                        default:
                            if (failCallBack != null) {
                                failCallBack.onFail(Config.RESULT_STATUS_FAIL);
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallBack != null) {
                        failCallBack.onFail(Config.RESULT_STATUS_FAIL);
                    }
                }
            }
        }, new NetConnection.FailCallBack() {

            @Override
            public void onFail() {
                if (failCallBack != null) {
                    failCallBack.onFail(Config.RESULT_STATUS_FAIL);
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_LOGIN, Config.KEY_PHONE_MD5, phone_md5, Config.KEY_CODE, code);
    }

    public static interface SuccessCallBack {
        void onSuccess(String token);
    }

    public static interface FailCallBack {
        void onFail(int errorCode);
    }
}
