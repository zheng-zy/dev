package com.zzy.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class StaggeredAdapter extends SimpleAdapter {

    private List<Integer> mHeights;

    public StaggeredAdapter(Context context, List<String> data) {
        super(context, data);

        mHeights = new ArrayList<Integer>();
        for (int i = 0;i<data.size();i++){
            mHeights.add((int) (100+Math.random()*300));
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //实现item动态高度的设置
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = mHeights.get(position);
        holder.itemView.setLayoutParams(layoutParams);

        holder.tv.setText(data.get(position));

        setUpItemEvent(holder);
    }
}

