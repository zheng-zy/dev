package com.zzy.secret.net;

import com.zzy.secret.Config;
import com.zzy.secret.md.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe 获取评论通信
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class NetGetComment {
    public NetGetComment(String phone_md5, String token, String msgId, int page, int perpage,
                         final SuccessCallBack successCallBack, final FailCallBack failCallBack) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallBack() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            List<Comment> comments = new ArrayList<Comment>();
                            JSONArray commentsjsonArray = jsonObject.getJSONArray(Config.KEY_COMMENTS);
                            JSONObject comment;
                            for (int i = 0; i < commentsjsonArray.length(); i++) {
                                comment = commentsjsonArray.getJSONObject(i);
                                comments.add(new Comment(comment.getString(Config.KEY_CONTENT), comment
                                        .getString(Config.KEY_PHONE_MD5)));
                            }

                            successCallBack.onSuccess(jsonObject.getString(Config.KEY_MSG_ID),
                                    jsonObject.getInt(Config.KEY_PAGE), jsonObject.getInt(Config.KEY_PREPAGE), comments);
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
        }, Config.KEY_ACTION, Config.ACTION_GET_COMMENT, Config.KEY_PHONE_MD5, phone_md5, Config.KEY_TOKEN, token,
                Config.KEY_MSG_ID, msgId, Config.KEY_PAGE, page + "", Config.KEY_PREPAGE, perpage + "");
    }

    public static interface SuccessCallBack {
        void onSuccess(String msgId, int page, int perpage, List<Comment> comments);
    }

    public static interface FailCallBack {
        void onFail(int errorCode);
    }
}
