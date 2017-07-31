package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.dongtanxiangqing.PIngLunFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.dongtanxiangqing.ZanFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.SeachAdapter;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.TanYiTanBean;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.Keys;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类描述:动弹详情
 */

public class DongTanXiangQingActivity extends BaseActivity {
    @Bind(R.id.action_bar)
    TextView actionBar;
    @Bind(R.id.item_dongtanxiangqing_author_head)
    ImageView itemDongtanxiangqingAuthorHead;
    @Bind(R.id.item_dongtanxiangqing_author_name)
    TextView itemDongtanxiangqingAuthorName;
    @Bind(R.id.item_dongtanxiangqing_author_body)
    TextView itemDongtanxiangqingAuthorBody;
    @Bind(R.id.item_dongtanxiangqing_author_date)
    TextView itemDongtanxiangqingAuthorDate;
    @Bind(R.id.item_dongtanxiangqing_author_phone)
    TextView itemDongtanxiangqingAuthorPhone;
    @Bind(R.id.item_dongtanxiangqing_author_zhuanfa)
    ImageView itemDongtanxiangqingAuthorZhuanfa;
    @Bind(R.id.item_dongtanxiangqing_author_pinlun)
    ImageView itemDongtanxiangqingAuthorPinlun;
    @Bind(R.id.item_dongtanxiangqing_author_zan)
    ImageView itemDongtanxiangqingAuthorZan;
    @Bind(R.id.item_dongtanxiangqing_tab)
    TabLayout itemDongtanxiangqingTab;
    @Bind(R.id.item_dongtanxiangqing_viewpager)
    ViewPager itemDongtanxiangqingViewpager;
    private Intent intent;
    private ArrayList<TanYiTanBean.TweetBean.UserBean> listZan;
    private String head;
    private String body;
    private String name;
    private String date;
    private List<String> listName;
    private List<BaseFragment> listFragment;
    private String zanSize;
    private ZanFragment zanFragment;
    private String id;
    private PIngLunFragment pIngLunFragment;
    private String pinlunsize;
    //    private PingLunFragment pingLunFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_dongtan_xiangqing;
    }

    @Override
    protected void initView() {
        intent = getIntent();
        listName = new ArrayList<>();
        listFragment = new ArrayList<>();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        actionBar.setText("动弹详情");
        listZan = intent.getParcelableArrayListExtra(Keys.DONGTAN_ZAN_LIST);
        head = intent.getStringExtra(Keys.DONGTAN_HEAD);
        id = intent.getStringExtra(Keys.DONGTAN_ID);
        body = intent.getStringExtra(Keys.DONGTAN_BODY);
        name = intent.getStringExtra(Keys.DONGTAN_NAME);
        date = intent.getStringExtra(Keys.DONGTAN_DATE);
        pinlunsize = intent.getStringExtra(Keys.DONGTAN_PINGLUN_SIZE);
        zanSize = intent.getStringExtra(Keys.DONGTAN_ZAN_SIZE);
        Glide.with(DongTanXiangQingActivity.this).load(head).into(itemDongtanxiangqingAuthorHead);
        itemDongtanxiangqingAuthorName.setText(name);
        itemDongtanxiangqingAuthorBody.setText(body);
        itemDongtanxiangqingAuthorDate.setText(date);
        getFragment();

    }

    /**
     * 生成Viewpagert和Fragment
     */
    private void getFragment() {
        listName.add("赞" + "(" + zanSize + ")");
        listName.add("评论(" + pinlunsize + ")");
        zanFragment = new ZanFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("zanFragment",listZan);
//        zanFragment.setArguments(bundle);
        zanFragment.setList(listZan);
        listFragment.add(zanFragment);
        pIngLunFragment = new PIngLunFragment();
        pIngLunFragment.setAuthorID(id);
        listFragment.add(pIngLunFragment);
        itemDongtanxiangqingViewpager.setAdapter(new SeachAdapter(App.baseActivity.getSupportFragmentManager()
                , listName, listFragment));
        itemDongtanxiangqingTab.setupWithViewPager(itemDongtanxiangqingViewpager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
