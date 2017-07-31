package com.jiyun.txl.kaiyuanchina_app.HomeFragment.zonghe;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.MyZiXunAdapter;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.InformetionBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss.HttpFactory;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.PullViewRefash;
import com.jiyun.txl.kaiyuanchina_app.Utils.Utils;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称: KaiYuanChina_App
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2017/4/25 16:46
 * 修改人: 田晓龙
 * 修改时间:
 * 修改备注:
 */

public class ReMenZiXunFragment extends BaseFragment implements PullToRefreshListener{
    @Bind(R.id.kaiyuanzixun_pullView)
    PullToRefreshRecyclerView kaiyuanzixunPullView;
    private List<InformetionBean.NewsBean> list;
    private MyZiXunAdapter myZiXunAdapter;
    private int index = 0;
    @Override
    protected int getlayout() {
        return R.layout.kaiyuanzixun;
    }

    @Override
    protected void initView(View view) {
        RecyclerView.LayoutManager manager  = new LinearLayoutManager(App.baseActivity);
        kaiyuanzixunPullView.setLayoutManager(manager);
        PullViewRefash.shuxing(kaiyuanzixunPullView);
        list = new ArrayList<>();
        myZiXunAdapter = new MyZiXunAdapter(App.baseActivity, R.layout.item_style,list);
        kaiyuanzixunPullView.setAdapter(myZiXunAdapter);

    }

    @Override
    protected void initData() {
        data();
    }

    private void data() {
        Map<String,String> map = new HashMap<>();
        map.put("catalog","4");
        map.put("pageIndex",String.valueOf(index));
        map.put("pageIndex","20");
        map.put("show","week");
        HttpFactory.getFactory().Get(Utils.NEWS_InFORMETION, map, new MyCallback() {
            @Override
            public void successful(String body) {
                XStream xStream = new XStream();
                xStream.alias("oschina", InformetionBean.class);
                xStream.alias("news", InformetionBean.NewsBean.class);
                InformetionBean informetion = (InformetionBean) xStream.fromXML(body);
                list.addAll(informetion.getNewslist());
                myZiXunAdapter.notifyDataSetChanged();
                kaiyuanzixunPullView.setRefreshComplete();
            }

            @Override
            public void failure(String errorMessage) {
                kaiyuanzixunPullView.setRefreshFail();
            }
        });
    }

    @Override
    protected void listener() {
kaiyuanzixunPullView.setPullToRefreshListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
      kaiyuanzixunPullView.post(new Runnable() {
          @Override
          public void run() {
              list.clear();
              index=0;
              data();
          }
      });
    }

    @Override
    public void onLoadMore() {
        kaiyuanzixunPullView.postDelayed(new Runnable() {
            @Override
            public void run() {
                index++;
                data();
                kaiyuanzixunPullView.setLoadMoreComplete();
            }
        },3000);
    }
}
