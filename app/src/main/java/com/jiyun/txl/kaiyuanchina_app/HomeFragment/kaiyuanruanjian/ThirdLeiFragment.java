package com.jiyun.txl.kaiyuanchina_app.HomeFragment.kaiyuanruanjian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.txl.kaiyuanchina_app.Activity.WebActivity;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.ErJiFenLeiLieBiaoBean;
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
 * 类描述:
 */

public class ThirdLeiFragment extends BaseFragment implements PullToRefreshListener{

    @Bind(R.id.kaiyuanzixun_pullView)
    PullToRefreshRecyclerView kaiyuanzixunPullView;
private List<ErJiFenLeiLieBiaoBean.SoftwareBean> list;
    private SharedPreferences sharedPreferences;
    private int index = 0;
    private ErjiFenLei erjiFenLei;
    @Override
    protected int getlayout() {
        return R.layout.kaiyuanzixun;
    }

    @Override
    protected void initView(View view) {
list = new ArrayList<>();
        sharedPreferences = App.baseActivity.getSharedPreferences("data", Context.MODE_PRIVATE);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(App.baseActivity);
        kaiyuanzixunPullView.setLayoutManager(manager);
        PullViewRefash.shuxing(kaiyuanzixunPullView);
        erjiFenLei = new ErjiFenLei(App.baseActivity,list);
        kaiyuanzixunPullView.setAdapter(erjiFenLei);
    }

    @Override
    protected void initData() {
        data();
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
    private void data(){
        Map<String,String> map = new HashMap<>();
        map.put("searchTag",sharedPreferences.getString("searchTag","309"));
        map.put("pageIndex",String.valueOf(index));
        map.put("pageSize","20");
        HttpFactory.getFactory().Get(Utils.KYRJ_SENDLIST, map, new MyCallback() {
            @Override
            public void successful(String body) {
                XStream xStream = new XStream();
                xStream.alias("oschina",ErJiFenLeiLieBiaoBean.class);
                xStream.alias("software",ErJiFenLeiLieBiaoBean.SoftwareBean.class);
                ErJiFenLeiLieBiaoBean erJiFenLeiLieBiaoBean = (ErJiFenLeiLieBiaoBean) xStream.fromXML(body);
                list.addAll(erJiFenLeiLieBiaoBean.getSoftwares());
                erjiFenLei.notifyDataSetChanged();
                kaiyuanzixunPullView.setRefreshComplete();
            }

            @Override
            public void failure(String errorMessage) {
                kaiyuanzixunPullView.setRefreshFail();
            }
        });
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
        },2000);
    }

    class  ErjiFenLei extends BaseAdapter<ErJiFenLeiLieBiaoBean.SoftwareBean>{


        public ErjiFenLei(Context context, List<ErJiFenLeiLieBiaoBean.SoftwareBean> datas) {
            super(context, R.layout.item_tuijian, datas);
        }

        @Override
        public void convert(ViewHolder holder, final ErJiFenLeiLieBiaoBean.SoftwareBean softwareBean) {
            holder.setText(R.id.tv_title,softwareBean.getName());
            holder.setText(R.id.tv_body,softwareBean.getDescription());
            holder.setOnclickListener(R.id.tv_lin, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                      intent.putExtra("url",softwareBean.getUrl());
                    intent.putExtra("webName","软件详情");
                    startActivity(intent);
                }
            });
        }
    }
}
