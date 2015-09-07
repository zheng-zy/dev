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
import com.zzy.secret.MD5Tool;
import com.zzy.secret.R;
import com.zzy.secret.net.NetGetCode;
import com.zzy.secret.net.NetLogin;

/**
 * Describe 登陆界面
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class AtyLogin extends AppCompatActivity implements View.OnClickListener {

    private Button btGetCode;
    private Button btLogin;
    private EditText etPhoneNum;
    private EditText etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        initView();
    }

    private void initView() {
        etPhoneNum = (EditText) findViewById(R.id.etPhoneNum);
        etCode = (EditText) findViewById(R.id.etCode);
        btGetCode = (Button) findViewById(R.id.btGetCode);
        btGetCode.setOnClickListener(this);
        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btGetCode:
                if (TextUtils.isEmpty(etPhoneNum.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.phone_number_cant_be_empty,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                final ProgressDialog pd = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.connecting),
                        getResources().getString(R.string.connecting_to_server));
                new NetGetCode(etPhoneNum.getText().toString(), new NetGetCode.SuccessCallBack() {
                    @Override
                    public void onSuccess() {
                        pd.dismiss();
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.success_to_get_code,
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }, new NetGetCode.FailCallBack() {
                    @Override
                    public void onFail() {
                        pd.dismiss();
                        Toast toast = Toast
                                .makeText(getApplicationContext(), R.string.fail_to_get_code, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
                break;
            case R.id.btLogin:
                if (TextUtils.isEmpty(etPhoneNum.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.phone_number_cant_be_empty,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                if (TextUtils.isEmpty(etCode.getText())) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.code_cant_be_empty, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                final ProgressDialog pdg = ProgressDialog.show(AtyLogin.this, getResources().getString(R.string.connecting),
                        getResources().getString(R.string.connecting_to_server));
                new NetLogin(MD5Tool.md5(etPhoneNum.getText().toString()), etCode.getText().toString(), new NetLogin.SuccessCallBack() {

                    @Override
                    public void onSuccess(String token) {
                        pdg.dismiss();
                        Config.cacheValue(AtyLogin.this, Config.KEY_TOKEN, token);
                        Config.cacheValue(AtyLogin.this, Config.KEY_PHONE_NUM, etPhoneNum.getText().toString());
                        Intent intent = new Intent(AtyLogin.this, AtyTimeline.class);
                        intent.putExtra(Config.KEY_TOKEN, token);
                        intent.putExtra(Config.KEY_PHONE_NUM, etPhoneNum.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                }, new NetLogin.FailCallBack() {

                    @Override
                    public void onFail(int errorCode) {
                        pdg.dismiss();
                        if (errorCode == Config.RESULT_STATUS_FAIL){
                            Toast toast = Toast.makeText(getApplicationContext(), R.string.fail_to_login, Toast.LENGTH_SHORT);
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
