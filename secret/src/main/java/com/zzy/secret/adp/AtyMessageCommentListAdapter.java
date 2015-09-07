package com.zzy.secret.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzy.secret.R;
import com.zzy.secret.md.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe 评论列表适配器
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class AtyMessageCommentListAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> comments = new ArrayList<Comment>();

    public AtyMessageCommentListAdapter(Context context) {
        this.context = context;
    }

    public void clear() {
        comments.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Comment> comments) {
        this.comments.addAll(comments);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.aty_comment_list, null);
            holder = new ViewHolder();
            holder.comment = (TextView) convertView.findViewById(R.id.tvComment);
            holder.phone_md5 = (TextView) convertView.findViewById(R.id.tv_phone_md5);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.comment.setText(comments.get(position).getContent());
        holder.phone_md5.setText(comments.get(position).getPhonr_md5());

        return convertView;
    }

    /**
     * listview 优化
     *
     * @author zhengzy
     *
     */
    private static class ViewHolder {
        TextView comment;
        TextView phone_md5;
    }

}
