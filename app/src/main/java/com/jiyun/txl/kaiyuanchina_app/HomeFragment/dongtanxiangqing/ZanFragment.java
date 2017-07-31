package com.jiyun.txl.kaiyuanchina_app.HomeFragment.dongtanxiangqing;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.PullToRefreshRecyclerView;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.DongTanXiangQingFragmentAdapter;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.TanYiTanBean;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类描述:点赞
 */

public class ZanFragment extends BaseFragment {
    @Bind(R.id.kaiyuanzixun_pullView)
    PullToRefreshRecyclerView kaiyuanzixunPullView;
    private ArrayList<TanYiTanBean.TweetBean.UserBean> list;
private DongTanXiangQingFragmentAdapter dongTanXiangQingFragmentAdapter;
    public ArrayList<TanYiTanBean.TweetBean.UserBean> getList() {
        dongTanXiangQingFragmentAdapter.notifyDataSetChanged();
        return list;

    }

    public void setList(ArrayList<TanYiTanBean.TweetBean.UserBean> list) {

        this.list = list;


    }

    @Override
    protected int getlayout() {
        return R.layout.kaiyuanzixun;
    }

    @Override
    protected void initView(View view) {
//        ArrayList<TanYiTanBean.TweetBean.UserBean> zanFragment = bundle.getParcelableArrayList("zanFragment");
//        for (TanYiTanBean.TweetBean.UserBean userBean : zanFragment) {
//            Log.e("userbean", userBean.getName()+"");
//        }
//             list = new ArrayList<>();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(App.baseActivity);
        kaiyuanzixunPullView.setLayoutManager(manager);
        dongTanXiangQingFragmentAdapter = new DongTanXiangQingFragmentAdapter(App.baseActivity,list);
        kaiyuanzixunPullView.setAdapter(dongTanXiangQingFragmentAdapter);
        getList();

        dongTanXiangQingFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {

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
