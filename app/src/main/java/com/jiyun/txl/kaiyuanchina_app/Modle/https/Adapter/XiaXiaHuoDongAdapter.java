package com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter;

import android.content.Context;
import android.widget.ImageView;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.XianXiaHuoDongBean;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.List;

/**
 * 类描述:线下活动
 */

public class XiaXiaHuoDongAdapter extends BaseAdapter<XianXiaHuoDongBean.EventBean> {

    public XiaXiaHuoDongAdapter(Context context, List<XianXiaHuoDongBean.EventBean> datas) {
        super(context, R.layout.item_xianxiahuodong, datas);
    }

    @Override
    public void convert(ViewHolder holder, XianXiaHuoDongBean.EventBean eventBean) {
        holder.setText(R.id.item_xianxia_Title,eventBean.getTitle());
        holder.setText(R.id.item_xianxia_date,eventBean.getStartTime());
        ImageView imageView  = holder.getView(R.id.item_xianxia_image);
        Glide.with(App.baseActivity).load(eventBean.getCover()).into(imageView);
        String status = eventBean.getStatus();
        if("3".equals(status)){
            holder.setText(R.id.item_xianxia_start,"截止报名");
        }else{
            holder.setText(R.id.item_xianxia_start,"正在报名");
        }

    }
}
