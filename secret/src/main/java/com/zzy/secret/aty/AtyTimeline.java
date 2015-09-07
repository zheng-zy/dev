package com.zzy.secret.aty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zzy.secret.Config;
import com.zzy.secret.MD5Tool;
import com.zzy.secret.R;
import com.zzy.secret.adp.AtyTimelineMessageListAdaper;
import com.zzy.secret.ld.LocalContact;
import com.zzy.secret.md.Message;
import com.zzy.secret.net.NetTimeline;
import com.zzy.secret.net.NetUploadContact;

import java.util.List;

/**
 * Describe 朋友圈消息
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class AtyTimeline extends AppCompatActivity {

    private String phoneNumber;
    private String token;
    private AtyTimelineMessageListAdaper adapter = null;
    private ListView lvTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);
        initView();
        initData();
        uploadContact();
    }

    private void uploadContact() {
        final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting),
                getResources().getString(R.string.connecting_to_server));
        new NetUploadContact(MD5Tool.md5(phoneNumber), token, LocalContact.getContactsJsonString(this),
                new NetUploadContact.SuccessCallBack() {

                    @Override
                    public void onSuccess() {
                        loadMessage();
                        pd.dismiss();
                    }

                }, new NetUploadContact.FailCallBack() {

            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                if (errorCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                    startActivity(new Intent(AtyTimeline.this, AtyLogin.class));
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.fail_to_upload_contacts,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private void initData() {
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
        phoneNumber = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
    }

    private void initView() {
        adapter = new AtyTimelineMessageListAdaper(this);
        lvTimeline = (ListView) findViewById(R.id.lv_timeline);
        lvTimeline.setAdapter(adapter);
        lvTimeline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message message = (Message) adapter.getItem(i);
                Intent intent = new Intent(AtyTimeline.this, AtyMessage.class);
                intent.putExtra(Config.KEY_MSG_ID, message.getMsgId());
                intent.putExtra(Config.KEY_MSG, message.getMsg());
                intent.putExtra(Config.KEY_PHONE_MD5, message.getPhone_md5());
                intent.putExtra(Config.KEY_TOKEN, token);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aty_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuShowAtyPublish:
                Intent intent = new Intent(AtyTimeline.this, AtyPublish.class);
                intent.putExtra(Config.KEY_PHONE_MD5, MD5Tool.md5(phoneNumber));
                intent.putExtra(Config.KEY_TOKEN, token);
                startActivityForResult(intent, 0);
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Config.ACTIVITY_RESULT_NEED_REFRESH:
                loadMessage();
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        System.out.println("onListItemClick");
//        super.onListItemClick(l, v, position, id);
//        Message message = (Message) adapter.getItem(position);
//        Intent intent = new Intent(this, AtyMessage.class);
//        intent.putExtra(Config.KEY_MSG_ID, message.getMsgId());
//        intent.putExtra(Config.KEY_MSG, message.getMsg());
//        intent.putExtra(Config.KEY_PHONE_MD5, message.getPhone_md5());
//        intent.putExtra(Config.KEY_TOKEN, token);
//        startActivity(intent);
//    }

    private void loadMessage() {

        System.out.println("loadMessage");

        final ProgressDialog pd = ProgressDialog.show(this, getResources().getString(R.string.connecting),
                getResources().getString(R.string.connecting_to_server));
        new NetTimeline(MD5Tool.md5(phoneNumber), token, 1, 20, new NetTimeline.SuccessCallBack() {

            @Override
            public void onSuccess(int page, int perpage, List<Message> messages) {
                pd.dismiss();
                adapter.clear();
                adapter.addAll(messages);
            }
        }, new NetTimeline.FailCallBack() {

            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                if (errorCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                    startActivity(new Intent(AtyTimeline.this, AtyLogin.class));
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.fail_to_load_timeline_data,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

}
