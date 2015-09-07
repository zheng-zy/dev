package com.zzy.secret.net;

import com.zzy.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Describe 获取验证码通信
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class NetGetCode {
    public NetGetCode(String phone, final SuccessCallBack successCallBack, final FailCallBack failCallBack) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallBack() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack != null) {
                                successCallBack.onSuccess();
                            }
                            break;
                        default:
                            if (failCallBack != null) {
                                failCallBack.onFail();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallBack != null) {
                        failCallBack.onFail();
                    }
                }
            }
        }, new NetConnection.FailCallBack() {

            @Override
            public void onFail() {
                if (failCallBack != null) {
                    failCallBack.onFail();
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_GET_CODE, Config.KEY_PHONE_NUM, phone);
    }

    public static interface SuccessCallBack {
        void onSuccess();
    }

    public static interface FailCallBack {
        void onFail();
    }
}
