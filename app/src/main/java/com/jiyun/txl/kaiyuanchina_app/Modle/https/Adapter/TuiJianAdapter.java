package com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.jiyun.txl.kaiyuanchina_app.Activity.WebActivity;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.TuiJianRJBean;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.List;

/**
 * 简单的pullview适配器
 */

public class TuiJianAdapter extends BaseAdapter<TuiJianRJBean.SoftwareBean> {

    public TuiJianAdapter(Context context, List<TuiJianRJBean.SoftwareBean> datas) {
        super(context, R.layout.item_tuijian, datas);
    }

    @Override
    public void convert(ViewHolder holder, final TuiJianRJBean.SoftwareBean softwareBean) {
holder.setText(R.id.tv_title,softwareBean.getName());
        holder.setText(R.id.tv_body,softwareBean.getDescription());
        holder.setOnclickListener(R.id.tv_lin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url",softwareBean.getUrl());
                intent.putExtra("webName","软件详情");
                context.startActivity(intent);
            }
        });
    }
}
