package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类描述:
 */

public class DrawActivity extends BaseActivity {
    @Bind(R.id.BIg_ImageView)
    ImageView BIgImageView;

    @Override
    protected int getLayout() {

        return R.layout.image;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();

        Glide.with(this).load(intent.getStringExtra("drawbe")).into(BIgImageView);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
