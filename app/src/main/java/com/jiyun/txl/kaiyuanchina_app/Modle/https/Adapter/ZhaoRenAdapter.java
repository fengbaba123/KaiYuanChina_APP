package com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter;

import android.content.Context;
import android.widget.ImageView;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.ZhaoRenSSBean;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.List;

/**
 * 类描述:找人的适配器
 */

public class ZhaoRenAdapter extends BaseAdapter<ZhaoRenSSBean.UserBean> {

    public ZhaoRenAdapter(Context context, List<ZhaoRenSSBean.UserBean> datas) {
        super(context, R.layout.item_zhaoren, datas);
    }

    @Override
    public void convert(ViewHolder holder, ZhaoRenSSBean.UserBean userBean) {
        holder.setText(R.id.item_zhaoren_author_Name, userBean.getName());
        holder.setText(R.id.item_zhaoren_author_address, userBean.getFrom());
        ImageView imageView = holder.getView(R.id.item_zhaoren_author_Head);
        Glide.with(context).load(userBean.getPortrait()).placeholder(R.mipmap.ic_launcher).into(imageView);
    }
}
