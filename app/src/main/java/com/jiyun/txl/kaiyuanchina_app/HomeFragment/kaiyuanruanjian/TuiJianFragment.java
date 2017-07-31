package com.jiyun.txl.kaiyuanchina_app.HomeFragment.kaiyuanruanjian;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.TuiJianAdapter;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.TuiJianRJBean;
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
 * 开源软件的推荐
 */

public class TuiJianFragment extends BaseFragment implements PullToRefreshListener{
    @Bind(R.id.tuijian_pullview)
    PullToRefreshRecyclerView tuijianPullview;
    private List<TuiJianRJBean.SoftwareBean> list;
private TuiJianAdapter tuiJianAdapter;
    private int index = 0;
    @Override
    protected int getlayout() {
        return R.layout.tuijian_fragment;
    }

    @Override
    protected void initView(View view) {
        list = new ArrayList<>();
        PullToRefreshRecyclerView.LayoutManager manager = new LinearLayoutManager(App.baseActivity);
        tuijianPullview.setLayoutManager(manager);
        PullViewRefash.shuxing(tuijianPullview);
        tuiJianAdapter = new TuiJianAdapter(App.baseActivity,list);
        tuijianPullview.setAdapter(tuiJianAdapter);
        data();
    }

    @Override
    protected void initData() {

    }

    private void data() {
        Map<String,String> map = new HashMap<>();
        map.put("searchTag","recommend");
        map.put("pageIndex",String.valueOf(index));
        map.put("pageSize","20");
        HttpFactory.getFactory().Get(Utils.KYRJ_TUIJIAN, map, new MyCallback() {
            @Override
            public void successful(String body) {
                XStream xStream = new XStream();
                xStream.alias("oschina",TuiJianRJBean.class);
                xStream.alias("software",TuiJianRJBean.SoftwareBean.class);
                TuiJianRJBean tuiJianRJBean = (TuiJianRJBean) xStream.fromXML(body);
                list.addAll(tuiJianRJBean.getSoftwares());
                tuiJianAdapter.notifyDataSetChanged();
                tuijianPullview.setRefreshComplete();
            }

            @Override
            public void failure(String errorMessage) {
                tuijianPullview.setRefreshFail();
            }
        });
    }

    @Override
    protected void listener() {
        tuijianPullview.setPullToRefreshListener(this);
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
        tuijianPullview.post(new Runnable() {
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
        tuijianPullview.postDelayed(new Runnable() {
            @Override
            public void run() {
                index++;
                data();
                tuijianPullview.setLoadMoreComplete();
            }
        },2000);
    }
}
