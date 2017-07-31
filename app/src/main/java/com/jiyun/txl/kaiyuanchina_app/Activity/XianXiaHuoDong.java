package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidkun.PullToRefreshRecyclerView;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.XiaXiaHuoDongAdapter;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.XianXiaHuoDongBean;
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
 * 类描述:   线下活动的Activity
 */

public class XianXiaHuoDong extends BaseActivity {
    @Bind(R.id.xianxia_pullView)
    PullToRefreshRecyclerView xianxiaPullView;
    private List<XianXiaHuoDongBean.EventBean> list;
private XiaXiaHuoDongAdapter xiaXiaHuoDongAdapter;
    private int pageIndex = 0;
    @Override
    protected int getLayout() {
        return R.layout.activity_xianxiahuodong;
    }

    @Override
    protected void initView() {
       list = new ArrayList<>();
        RecyclerView.LayoutManager manager  =new LinearLayoutManager(App.baseActivity);
        xianxiaPullView.setLayoutManager(manager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        Map<String,String> map = new HashMap<>();
        map.put("pageIndex",String.valueOf(pageIndex));
        map.put("uid","0");
        map.put("pageSize","20");
        HttpFactory.getFactory().Get(Utils.XIANXIA_HUODONG, map, new MyCallback() {
            @Override
            public void successful(String body) {
                XStream xStream = new XStream();
                xStream.alias("oschina",XianXiaHuoDongBean.class);
                xStream.alias("event",XianXiaHuoDongBean.EventBean.class);
                XianXiaHuoDongBean xianXiaHuoDongBean = (XianXiaHuoDongBean) xStream.fromXML(body);
                list.addAll(xianXiaHuoDongBean.getEvents());
                xiaXiaHuoDongAdapter = new XiaXiaHuoDongAdapter(App.baseActivity,list);
                xianxiaPullView.setAdapter(xiaXiaHuoDongAdapter);
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
}
