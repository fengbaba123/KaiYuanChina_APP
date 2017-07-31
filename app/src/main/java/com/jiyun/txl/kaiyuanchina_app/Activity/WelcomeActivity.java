package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.MainActivity;
import com.jiyun.txl.kaiyuanchina_app.R;

/**
 * 类描述:欢迎页面
 */

public class WelcomeActivity extends BaseActivity {
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
handler.sendEmptyMessageDelayed(1,2000);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }
}
