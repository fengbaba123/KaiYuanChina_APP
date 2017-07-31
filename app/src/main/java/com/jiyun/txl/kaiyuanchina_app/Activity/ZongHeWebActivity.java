package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.NewsDtilsBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss.HttpFactory;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.Utils;
import com.thoughtworks.xstream.XStream;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述:
 */

public class ZongHeWebActivity extends BaseActivity {
    @Bind(R.id.web_back)
    ImageView webBack;
    @Bind(R.id.web_text)
    TextView webText;
    @Bind(R.id.web_pinlun)
    TextView webPinlun;
    @Bind(R.id.web_webView)
    WebView webWebView;
    @Bind(R.id.fabiaopinglun)
    EditText fabiaopinglun;
    @Bind(R.id.shoucang)
    ImageView shoucang;
    @Bind(R.id.fabiao_btn)
    Button fabiaoBtn;
    private String zixunid;
    private Intent intent;
    private SharedPreferences sharedPreferences;

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        intent = getIntent();
        zixunid = intent.getStringExtra("zixunid");
        webText.setText("资讯详情");
        webWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", zixunid);
        HttpFactory.getFactory().Get(Utils.NEWS_DITAIS, map, new MyCallback() {
            @Override
            public void successful(String body) {

                XStream xStream = new XStream();
                xStream.alias("oschina", NewsDtilsBean.class);
                xStream.alias("relative", NewsDtilsBean.NewsBean.RelativeBean.class);
                NewsDtilsBean newsDtilsBean = (NewsDtilsBean) xStream.fromXML(body);
                webPinlun.setText(newsDtilsBean.getNews().getCommentCount());
                webWebView.loadUrl(newsDtilsBean.getNews().getUrl());

            }

            @Override
            public void failure(String errorMessage) {

            }
        });

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

    @OnClick({R.id.shoucang, R.id.fabiao_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.shoucang:
                break;
            case R.id.fabiao_btn:
                if(TextUtils.isEmpty(fabiaopinglun.getText())){
                    Toast.makeText(this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,String> map = new HashMap<>();
                map.put("catalog","1");
                map.put("id",zixunid);
                map.put("uid",sharedPreferences.getString("uid",""));
                map.put("content",fabiaopinglun.getText().toString().trim());
                map.put("isPostToMyZone","0");
                HttpFactory.getFactory().Post(Utils.FABIAO_PINGLUN, map, new MyCallback() {
                    @Override
                    public void successful(String body) {
                        Log.e("pinglun",body);
                        Toast.makeText(ZongHeWebActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                        fabiaopinglun.setText("");
                    }

                    @Override
                    public void failure(String errorMessage) {

                    }
                });
                break;
        }
    }
}
