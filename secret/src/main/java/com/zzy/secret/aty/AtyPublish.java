package com.zzy.secret.aty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zzy.secret.Config;
import com.zzy.secret.R;
import com.zzy.secret.net.NetPublish;

/**
 * Describe 发表消息
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class AtyPublish extends AppCompatActivity implements View.OnClickListener {

    private EditText etMessageContent;
    private Button btPublish;
    private String phone_md5;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_publish);
        initView();
        initData();
    }

    private void initData() {
        phone_md5 = getIntent().getStringExtra(Config.KEY_PHONE_MD5);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
    }

    private void initView() {
        etMessageContent = (EditText) findViewById(R.id.etMessageContent);
        btPublish = (Button) findViewById(R.id.btPublish);
        btPublish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btPublish:
                if (TextUtils.isEmpty(etMessageContent.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.publish_content_cant_be_empty,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting),
                        getResources().getString(R.string.connecting_to_server));
                new NetPublish(phone_md5, token, etMessageContent.getText().toString(), new NetPublish.SuccessCallBack() {

                    @Override
                    public void onSuccess() {
                        pd.dismiss();
                        setResult(Config.ACTIVITY_RESULT_NEED_REFRESH);
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.success_to_publish,
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        finish();
                    }
                }, new NetPublish.FailCallBack() {

                    @Override
                    public void onFail(int errorCode) {
                        pd.dismiss();
                        if (errorCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                            startActivity(new Intent(AtyPublish.this, AtyLogin.class));
                            finish();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), R.string.fail_to_publish,
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                });
                break;

            default:
                break;
        }
    }

}
