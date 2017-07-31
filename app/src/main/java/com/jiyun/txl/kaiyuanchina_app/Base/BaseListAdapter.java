package com.jiyun.txl.kaiyuanchina_app.Base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 类描述 用于ListView的封装
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> list;
    private LayoutInflater layoutInflater;
    private int LayoutID;

    public BaseListAdapter(Context context, List<T> list, int layoutID) {
        this.context = context;
        this.list = list;
        LayoutID = layoutID;
        layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //把这个适配器的convertView context LayoutID传过去
        ViewHolder instance = ViewHolder.getInstance(context, convertView, LayoutID);
        //通过ViewHolder得到itemview 就是每个item
        View itemView = instance.getItemView();
        Log.e("框架", list.get(position) + "");
        //调用方法 不用说
        convert(instance, list.get(position));
        return itemView;
    }

    //这个方法就用来赋值的
    protected abstract void convert(ViewHolder holder, T t);
}
