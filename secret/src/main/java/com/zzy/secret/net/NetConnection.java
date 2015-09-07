package com.zzy.secret.net;

import android.os.AsyncTask;

import com.zzy.secret.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Describe 网络通信基类
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */

public class NetConnection {
    public NetConnection(final String url, final HttpMethod method, final SuccessCallBack successCallBack,
                         final FailCallBack failCallBack, final String... kvs) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {

                StringBuffer paramStr = new StringBuffer();
                for (int i = 0; i < kvs.length; i = i + 2) {
                    paramStr.append(kvs[i]).append("=").append(kvs[i + 1]).append("&");
                }

                try {
                    URLConnection uc;
                    switch (method) {
                        case POST:
                            uc = new URL(url).openConnection();
                            uc.setDoOutput(true);
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(),
                                    Config.CHARSET));
                            bw.write(paramStr.toString());
                            bw.flush();
                            break;
                        default:
                            uc = new URL(url + "?" + paramStr.toString()).openConnection();
                            break;
                    }

                    System.out.println("Request url:" + uc.getURL());
                    System.out.println("Request data:" + paramStr);

                    BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(), Config.CHARSET));
                    String line = null;
                    StringBuffer result = new StringBuffer();
                    while ((line = br.readLine()) != null) {
                        result.append(line);
                    }
                    System.out.println("result:" + result);
                    return result.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            /**
             * onPostExecute执行在doInBackground之后, doInBackground返回值是onPostExecute参数
             */

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    if (successCallBack != null) {
                        successCallBack.onSuccess(result);
                    }
                } else {
                    if (failCallBack != null) {
                        failCallBack.onFail();
                    }
                }
            }
        }.execute();

    }

    public static interface SuccessCallBack {
        void onSuccess(String result);
    }

    public static interface FailCallBack {
        void onFail();
    }
}