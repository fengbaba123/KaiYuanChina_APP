package com.jiyun.txl.kaiyuanchina_app.HomeFragment.dongtanxiangqing;

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
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.DongTanPingLunAdapter;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.HuoQuPingLunBean;
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
 * 类描述:评论
 */

public class PIngLunFragment extends BaseFragment {
    @Bind(R.id.kaiyuanzixun_pullView)
    PullToRefreshRecyclerView kaiyuanzixunPullView;
       private DongTanPingLunAdapter dongTanPingLunAdapter;
private String authorID;
private List<HuoQuPingLunBean.CommentBean> list;
    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    @Override
    protected int getlayout() {
        return R.layout.kaiyuanzixun;
    }

    @Override
    protected void initView(View view) {
   list = new ArrayList<>();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(App.baseActivity);
        kaiyuanzixunPullView.setLayoutManager(manager);
        dongTanPingLunAdapter = new DongTanPingLunAdapter(App.baseActivity,list);
        kaiyuanzixunPullView.setAdapter(dongTanPingLunAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void listener() {
        Map<String,String> map = new HashMap<>();
        map.put("catalog","3");
        map.put("id",authorID);
        map.put("pageIndex","0");
        map.put("pageSize","20");
        HttpFactory.getFactory().Get(Utils.HUOQU_PINGLUN, map, new MyCallback() {
            @Override
            public void successful(String body) {
                Log.e("pinglunbody",body);
                XStream xStream = new XStream();
                xStream.alias("oschina", HuoQuPingLunBean.class);
                xStream.alias("comment",HuoQuPingLunBean.CommentBean.class);
                HuoQuPingLunBean huoQuPingLunBean = (HuoQuPingLunBean) xStream.fromXML(body);
                list.addAll(huoQuPingLunBean.getComments());
                dongTanPingLunAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(String errorMessage) {

            }
        });
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
