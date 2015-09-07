package com.zzy.secret.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzy.secret.R;
import com.zzy.secret.md.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe 消息列表适配器
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class AtyTimelineMessageListAdaper extends BaseAdapter {

    private List<Message> messages = new ArrayList<Message>();
    private Context context = null;

    public void clear() {
        messages.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Message> messages) {
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public AtyTimelineMessageListAdaper(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // listview 优化
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.aty_timeline_list, null);
            holder = new ViewHolder();
            holder.msg = (TextView) convertView.findViewById(R.id.tvMsg);
            holder.msgId = (TextView) convertView.findViewById(R.id.tvMsgId);
            holder.msg.setText(messages.get(position).getMsg());
            holder.msgId.setText(messages.get(position).getMsgId());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.msg.setText(messages.get(position).getMsg());
            holder.msgId.setText(messages.get(position).getMsgId());
        }

        return convertView;
    }

    /**
     * listview 优化
     *
     * @author Administrator
     *
     */
    private static class ViewHolder {
        TextView msg;
        TextView msgId;
    }

}
