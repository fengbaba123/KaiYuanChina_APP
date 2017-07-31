package com.jiyun.txl.kaiyuanchina_app.HomeFragment.kaiyuanruanjian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.txl.kaiyuanchina_app.Activity.WebActivity;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
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
 * 开源软件 最新
 */

public class NewsKYFragment extends BaseFragment implements PullToRefreshListener{
    @Bind(R.id.tuijian_pullview)
    PullToRefreshRecyclerView tuijianPullview;
    private List<TuiJianRJBean.SoftwareBean> list1;
    private int index = 0;
    private Myadapter myadapter;
    @Override
    protected int getlayout() {
        return R.layout.tuijian_fragment;
    }

    @Override
    protected void initView(View view) {
list1 = new ArrayList<>();
        PullToRefreshRecyclerView.LayoutManager manager = new LinearLayoutManager(App.baseActivity);
        tuijianPullview.setLayoutManager(manager);
        PullViewRefash.shuxing(tuijianPullview);
        myadapter = new Myadapter(App.baseActivity,list1);
        tuijianPullview.setAdapter(myadapter);
    }

    @Override
    protected void initData() {
        data();
    }

    private void data() {
        Map<String,String> map = new HashMap<>();
        map.put("searchTag","time");
        map.put("pageIndex",String.valueOf(index));
        map.put("pageSize","20");
        HttpFactory.getFactory().Get(Utils.KYRJ_TUIJIAN, map, new MyCallback() {
            @Override
            public void successful(String body) {
                XStream xStream = new XStream();
                xStream.alias("oschina",TuiJianRJBean.class);
                xStream.alias("software",TuiJianRJBean.SoftwareBean.class);
                TuiJianRJBean tuiJianRJBean = (TuiJianRJBean) xStream.fromXML(body);
                list1.addAll(tuiJianRJBean.getSoftwares());
                Toast.makeText(App.baseActivity, "哈哈哈"+list1.size(), Toast.LENGTH_SHORT).show();
                myadapter.notifyDataSetChanged();
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
                list1.clear();
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

    class Myadapter extends BaseAdapter<TuiJianRJBean.SoftwareBean>{
        public Myadapter(Context context, List<TuiJianRJBean.SoftwareBean> datas) {
            super(context, R.layout.item_tuijian, datas);
        }

        @Override
        public void convert(ViewHolder holder, final TuiJianRJBean.SoftwareBean softwareBean) {
            holder.setText(R.id.tv_title,softwareBean.getName());
            holder.setText(R.id.tv_body,softwareBean.getDescription());
            holder.setOnclickListener(R.id.tv_lin, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url",softwareBean.getUrl());
                    intent.putExtra("webName","软件详情");
                    context.startActivity(intent);
                }
            });
        }
    }
}
