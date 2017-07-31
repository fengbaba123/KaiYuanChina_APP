package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.kaiyuanruanjian.FengLeiFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.kaiyuanruanjian.GuoChanFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.kaiyuanruanjian.NewsKYFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.kaiyuanruanjian.ReMenKYFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.kaiyuanruanjian.TuiJianFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.SeachAdapter;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 类描述
 */
public class KaiYuanActivity extends BaseActivity {
    @Bind(R.id.action_bar)
    TextView actionBar;
    @Bind(R.id.zonghe_title)
    TabLayout zongheTitle;
    @Bind(R.id.zonghe_add)
    ImageView zongheAdd;
    @Bind(R.id.zonghe_viewpager)
    ViewPager zongheViewpager;
    //用于存放Tab名字
    private List<String> listName;
    //用于存放Fragment
    private List<BaseFragment> listFragment;

    /**
     * @return 你的View视图
     */
    @Override
    protected int getLayout() {
        return R.layout.kaiyuanruanjian;
    }


    /**
     * 初始化VIew组件
     */
    @Override
    protected void initView() {
        zongheTitle = (TabLayout) findViewById(R.id.zonghe_title);
        zongheViewpager = (ViewPager) findViewById(R.id.zonghe_viewpager);
        zongheAdd = (ImageView) findViewById(R.id.zonghe_add);
        listName = new ArrayList<>();
        listFragment = new ArrayList<>();
    }

    /**
     * 实现监听
     */
    @Override
    protected void initListener() {
    }

    @Override
    protected void loadData() {
        listName.add("分类");
        listName.add("推荐");
        listName.add("最新");
        listName.add("热门");
        listName.add("国产");
        listFragment.add(new FengLeiFragment());
        listFragment.add(new TuiJianFragment());
        listFragment.add(new NewsKYFragment());
        listFragment.add(new ReMenKYFragment());
        listFragment.add(new GuoChanFragment());

        zongheViewpager.setAdapter(new SeachAdapter(getSupportFragmentManager(),
                listName, listFragment));
        zongheTitle.setupWithViewPager(zongheViewpager);
    }

    @Override
    public void onBackPressed() {

        if (zongheViewpager.getCurrentItem() != 0) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
