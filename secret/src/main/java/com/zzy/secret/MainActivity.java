package com.zzy.secret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zzy.secret.aty.AtyLogin;
import com.zzy.secret.aty.AtyTimeline;

/**
 * Describe 主函数
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        startActivity(new Intent(this, AtyTest.class));
//        startActivity(new Intent(this, AtyLogin.class));

        String token = Config.getCachedValue(this, Config.KEY_TOKEN);
        String phoneNumber = Config.getCachedValue(this, Config.KEY_PHONE_NUM);
        if (token != null && phoneNumber != null) {
            Intent intent = new Intent(this, AtyTimeline.class);
            intent.putExtra(Config.KEY_TOKEN, token);
            intent.putExtra(Config.KEY_PHONE_NUM, phoneNumber);
            startActivity(intent);
        } else {
            startActivity(new Intent(this, AtyLogin.class));
        }
        finish();
    }

}
