package com.zzy.secret.ld;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.zzy.secret.Config;
import com.zzy.secret.MD5Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Describe 获取本地联系人手机号
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class LocalContact {
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    /**
     * 获取本地联系人
     *
     * @param context
     * @return
     */
    public static String getContactsJsonString(Context context) {
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        String phoneNumber;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        while (cursor.moveToNext()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (phoneNumber.charAt(0) == '+' && phoneNumber.charAt(1) == '8' && phoneNumber.charAt(2) == '6') {
                phoneNumber = phoneNumber.substring(3);
            }
            jsonObject = new JSONObject();
            try {
                jsonObject.put(Config.KEY_PHONE_NUM, MD5Tool.md5(phoneNumber));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
            System.out.println(phoneNumber);
        }
        return jsonArray.toString();
    }
}
