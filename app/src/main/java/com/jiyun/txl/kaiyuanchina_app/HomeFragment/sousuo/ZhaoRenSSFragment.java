package com.jiyun.txl.kaiyuanchina_app.HomeFragment.sousuo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.PullToRefreshRecyclerView;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.ZhaoRenAdapter;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.ZhaoRenSSBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss.HttpFactory;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.Utils;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类描述:  搜索的找人Fragment
 */

public class ZhaoRenSSFragment extends BaseFragment {
    @Bind(R.id.kaiyuanzixun_pullView)
    PullToRefreshRecyclerView kaiyuanzixunPullView;
    private List<ZhaoRenSSBean.UserBean> list;
private SharedPreferences sharedPreferences;
    @Override
    protected int getlayout() {
        return R.layout.kaiyuanzixun;
    }
private ZhaoRenAdapter zhaoRenAdapter;
    @Override
    protected void initView(View view) {
        list = new ArrayList<>();
        sharedPreferences = App.baseActivity.getSharedPreferences("data", Context.MODE_PRIVATE);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(App.baseActivity);
        kaiyuanzixunPullView.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
     Map<String,String> map = new HashMap<>();
        map.put("name",sharedPreferences.getString("sousuo",""));
        HttpFactory.getFactory().Get(Utils.SERCGH_ZHAOREN, map, new MyCallback() {
            @Override
            public void successful(String body) {
                Log.e("zhaoren",body+"");
                XStream xStream = new XStream();
                xStream.alias("oschina",ZhaoRenSSBean.class);
                xStream.alias("user",ZhaoRenSSBean.UserBean.class);
                ZhaoRenSSBean zhaoRenSSBean = (ZhaoRenSSBean) xStream.fromXML(body);
                list.addAll(zhaoRenSSBean.getUsers());
                zhaoRenAdapter = new ZhaoRenAdapter(App.baseActivity,list);
                kaiyuanzixunPullView.setAdapter(zhaoRenAdapter);
            }

            @Override
            public void failure(String errorMessage) {

            }
        });
    }

    @Override
    protected void listener() {

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
}
