package com.jiyun.txl.kaiyuanchina_app.HomeFragment.dongtan;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.MyDongTanViewAdapter;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.TanYiTanBean;
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
 * 热门动弹
 */

public class MyDongTanFragment extends BaseFragment implements PullToRefreshListener {

    @Bind(R.id.ZuixinDongtan_view)
    PullToRefreshRecyclerView ZuixinDongtanView;
    private List<TanYiTanBean.TweetBean> list;
    private int index = 0;
    private MyDongTanViewAdapter pullViewAdapter;
private SharedPreferences sharedPreferences;
    @Override
    protected int getlayout() {
        return R.layout.zuixinrimenidongtan;
    }

    @Override
    protected void initView(View view) {
        sharedPreferences = App.baseActivity.getSharedPreferences("data", Context.MODE_PRIVATE);
        ZuixinDongtanView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        PullViewRefash.shuxing(ZuixinDongtanView);
        list = new ArrayList<>();
        pullViewAdapter = new MyDongTanViewAdapter(getActivity().getApplicationContext()
                , list);
        ZuixinDongtanView.setAdapter(pullViewAdapter);
    }

    @Override
    protected void initData() {
        data();

    }

    private void data() {
        sharedPreferences = App.baseActivity.getSharedPreferences("data",Context.MODE_PRIVATE);
        Map<String, String> map = new HashMap<>();
        if(sharedPreferences.getString("uid","").isEmpty()){
            map.put("uid","1");
        }else{
            map.put("uid",sharedPreferences.getString("uid","") );
        }
        map.put("pageIndex", String.valueOf(index));
        map.put("pageSize", "20");

        HttpFactory.getFactory().Get(Utils.DONGTAN_ZUIXIN, map, new MyCallback() {
            @Override
            public void successful(String body) {
                XStream xstream = new XStream();
                xstream.alias("oschina", TanYiTanBean.class);
                xstream.alias("tweet", TanYiTanBean.TweetBean.class);
                xstream.alias("user", TanYiTanBean.TweetBean.UserBean.class);
                TanYiTanBean tanYiTanBean = (TanYiTanBean) xstream.fromXML(body);
                list.addAll(tanYiTanBean.getTweets());
                pullViewAdapter.notifyDataSetChanged();
                ZuixinDongtanView.setRefreshComplete();
            }

            @Override
            public void failure(String errorMessage) {
                Toast.makeText(getActivity().getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
                ZuixinDongtanView.setRefreshFail();
            }
        });
    }

    @Override
    protected void listener() {
        ZuixinDongtanView.setPullToRefreshListener(this);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        ZuixinDongtanView.postDelayed(new Runnable() {
            @Override
            public void run() {
                index = 0;
                list.clear();
                data();

            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        ZuixinDongtanView.postDelayed(new Runnable() {
            @Override
            public void run() {
                index++;
                data();
                ZuixinDongtanView.setLoadMoreComplete();
            }
        }, 2000);

    }


}
