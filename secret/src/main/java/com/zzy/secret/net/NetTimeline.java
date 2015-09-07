package com.zzy.secret.net;

import com.zzy.secret.Config;
import com.zzy.secret.md.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe 获取消息列表
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class NetTimeline {
    public NetTimeline(String phone_md5, String token, int page, int perpage, final SuccessCallBack successCallBack,
                    final FailCallBack failCallBack) {
        new NetConnection(
                Config.SERVER_URL,
                HttpMethod.POST,
                new NetConnection.SuccessCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            switch (jsonObject.getInt(Config.KEY_STATUS)) {
                                case Config.RESULT_STATUS_SUCCESS:
                                    if (successCallBack != null) {
                                        List<Message> messages = new ArrayList<Message>();
                                        JSONArray jsonArray =  jsonObject.getJSONArray(Config.KEY_ITEMS);
                                        JSONObject msgObj;
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            msgObj = jsonArray.getJSONObject(i);
                                            messages.add(new Message(msgObj.getString(Config.KEY_MSG_ID), msgObj.getString(Config.KEY_MSG), msgObj.getString(Config.KEY_PHONE_MD5)));
                                        }
                                        successCallBack.onSuccess(jsonObject.getInt(Config.KEY_PAGE),
                                                jsonObject.getInt(Config.KEY_PREPAGE),
                                                messages);
                                    }
                                    break;
                                case Config.RESULT_STATUS_INVALID_TOKEN:
                                    if (failCallBack != null) {
                                        failCallBack.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
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
        }, Config.KEY_ACTION, Config.ACTION_TIMELINE, Config.KEY_PHONE_MD5, phone_md5, Config.KEY_TOKEN, token,
                Config.KEY_PAGE, page + "", Config.KEY_PREPAGE, perpage + "");
    }

    public static interface SuccessCallBack {
        void onSuccess(int page, int perpage, List<Message> timeline);
    }

    public static interface FailCallBack {
        void onFail(int errorCode);
    }
}
