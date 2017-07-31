package com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.jiyun.txl.kaiyuanchina_app.Activity.WebActivity;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.TuiJianBokeBean;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.Dates;
import com.jiyun.txl.kaiyuanchina_app.Utils.Keys;

import java.util.List;

/**
 * 类描述:推荐博客适配器
 */

public class TuiJianBoKeAdapter extends BaseAdapter<TuiJianBokeBean.BlogBean> {

    public TuiJianBoKeAdapter(Context context, List<TuiJianBokeBean.BlogBean> datas) {
        super(context, R.layout.item_tuijianblog, datas);
    }

    @Override
    public void convert(ViewHolder holder, final TuiJianBokeBean.BlogBean blogBean) {
        holder.setText(R.id.tuijian_blog_title, blogBean.getTitle());
        holder.setText(R.id.tuijian_blog_body, blogBean.getBody());
        holder.setText(R.id.tuijian_blog_author, "@" + blogBean.getAuthorname());
        holder.setText(R.id.tuijian_blog_pinlun, " " + blogBean.getCommentCount());
        String date = Dates.getDate(blogBean.getPubDate());
        holder.setText(R.id.tuijian_blog_date, date);
        if (Dates.getToday(blogBean.getPubDate())) {
            holder.setImageResource(R.id.tuijian_blog_today, R.drawable.ic_label_today);
        }
        holder.setOnclickListener(R.id.tuijian_blog_lin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent in = new Intent(context, WebActivity.class);
                in.putExtra("url",blogBean.getUrl());
                in.putExtra(Keys.WEB_NAME,"推荐博客");
                in.putExtra(Keys.WEB_COMMENT,blogBean.getCommentCount());
                context.startActivity(in);
            }
        });
    }
}
