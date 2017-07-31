package com.jiyun.txl.kaiyuanchina_app.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jiyun.txl.kaiyuanchina_app.App;

import butterknife.ButterKnife;

/**
 * 封装Activity
 */

public abstract class BaseActivity extends AppCompatActivity {
    //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    //这个用于存上一次显示的Fragment
     BaseFragment lastFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.baseActivity = this;
        setContentView(getLayout());
        ButterKnife.bind(this);
        initView();
        loadData();
        initListener();

    }

    /**
     * 当Activity再次运行的时候 把this继续复制给baseActivity
     */
    @Override
    protected void onResume() {
        super.onResume();
        App.baseActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

}

    //找布局
    protected abstract int getLayout();

    //找n你布局ID
    protected abstract void initView();
    //加载数据
    protected abstract void loadData();
    //实现你各个组件的监听
    protected abstract void initListener();


    //把你的fragment加到那个视图上


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
