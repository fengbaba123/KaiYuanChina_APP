package com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.TanYiTanBean;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.List;

/**
 * 类描述:动弹详情
 */

public class DongTanXiangQingFragmentAdapter extends BaseAdapter<TanYiTanBean.TweetBean.UserBean> {

    public DongTanXiangQingFragmentAdapter(Context context, List<TanYiTanBean.TweetBean.UserBean> datas) {
        super(context, R.layout.item_dongtanxiangqing_zan, datas);
    }

    @Override
    public void convert(ViewHolder holder, TanYiTanBean.TweetBean.UserBean userBean) {
        holder.setText(R.id.item_dontanxiangqing_zanNameList,userBean.getName());
        final ImageView imageView = holder.getView(R.id.item_dongtanxiangqing_zanlist);
        Glide.with(App.baseActivity).load(userBean.getPortrait())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }
}
