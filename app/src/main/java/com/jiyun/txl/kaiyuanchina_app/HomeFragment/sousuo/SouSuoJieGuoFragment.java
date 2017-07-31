package com.jiyun.txl.kaiyuanchina_app.HomeFragment.sousuo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.SeachAdapter;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用于展示搜索结果的Fragment
 */

public class SouSuoJieGuoFragment extends BaseFragment {
    @Bind(R.id.souuso_tab)
    TabLayout souusoTab;
    @Bind(R.id.sousuo_viewpager)
    ViewPager sousuoViewpager;
    private List<String> listName;
    private List<BaseFragment> list;

    @Override
    protected int getlayout() {
        return R.layout.sousuojieguo_fragment;
    }

    @Override
    protected void initView(View view) {
        list = new ArrayList<>();
        listName = new ArrayList<>();
    }

    @Override
    protected void initData() {
        listName.add("软件");
        listName.add("博客");
        listName.add("资讯");
        listName.add("问答");
        listName.add("找人");
            list.add(new RuanJianSSFragment());
            list.add(new BoKeSSFragment());
            list.add(new ZiXunSSFragment());
            list.add(new WenDaSSFragment());
        list.add(new ZhaoRenSSFragment());
        sousuoViewpager.setAdapter(new SeachAdapter(getActivity().getSupportFragmentManager()
                , listName, list));
        souusoTab.setupWithViewPager(sousuoViewpager);
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
