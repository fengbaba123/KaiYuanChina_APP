package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述:web页面
 */

public class WebActivity extends BaseActivity {


    @Bind(R.id.web_back)
    ImageView webBack;
    @Bind(R.id.web_text)
    TextView webText;
    @Bind(R.id.web_pinlun)
    TextView iwebPinlun;
    @Bind(R.id.web_webView)
    WebView webWebView;
    private Intent intent;
    private String url;
    private String comment;
    private String webName;

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        intent = getIntent();
        webWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        url = intent.getStringExtra("url");
        comment = intent.getStringExtra("comment");
        webName = intent.getStringExtra("webName");
        if(comment!=null){
            iwebPinlun.setText(comment);
        }else{
            iwebPinlun.setVisibility(View.GONE);
        }
        webText.setText(webName);
        webWebView.loadUrl(url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick(R.id.web_back)
    public void onViewClicked() {
        finish();
    }
}
