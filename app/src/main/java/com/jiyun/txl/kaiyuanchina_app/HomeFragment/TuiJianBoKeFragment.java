package com.jiyun.txl.kaiyuanchina_app.HomeFragment;

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
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.TuiJianBoKeAdapter;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.TuiJianBokeBean;
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
 * 类描述:   综合推荐博客
 * 创建人: 田晓龙
 * 创建时间: 2017/4/19 14:28
 * 修改人: 田晓龙
 * 修改时间:
 * 修改备注:
 */

public class TuiJianBoKeFragment extends BaseFragment implements PullToRefreshListener {
    @Bind(R.id.kaiyuanzixun_pullView)
    PullToRefreshRecyclerView kaiyuanzixunPullView;
    private List<TuiJianBokeBean.BlogBean> list;
    private int index = 0;
    private TuiJianBoKeAdapter tuiJianBoKeAdapter;

    @Override
    protected int getlayout() {
        return R.layout.kaiyuanzixun;
    }

    @Override
    protected void initView(View view) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(App.baseActivity);
        kaiyuanzixunPullView.setLayoutManager(manager);
        list = new ArrayList<>();
        PullViewRefash.shuxing(kaiyuanzixunPullView);
        tuiJianBoKeAdapter = new TuiJianBoKeAdapter(App.baseActivity, list);
        kaiyuanzixunPullView.setAdapter(tuiJianBoKeAdapter);
    }

    @Override
    protected void initData() {

        data();
    }

    private void data() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "recommend");
        map.put("index", String.valueOf(index));
        map.put("pageSize", "20");
        HttpFactory.getFactory().Get(Utils.TUIJIAN_BOKE, map, new MyCallback() {
            @Override
            public void successful(String body) {
                XStream xStream = new XStream();
                xStream.alias("oschina", TuiJianBokeBean.class);
                xStream.alias("blog", TuiJianBokeBean.BlogBean.class);
                TuiJianBokeBean tuiJianBokeBean = (TuiJianBokeBean) xStream.fromXML(body);
                list.addAll(tuiJianBokeBean.getBlogs());
                tuiJianBoKeAdapter.notifyDataSetChanged();
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
                index = 0;
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
        }, 2000);
    }
}
