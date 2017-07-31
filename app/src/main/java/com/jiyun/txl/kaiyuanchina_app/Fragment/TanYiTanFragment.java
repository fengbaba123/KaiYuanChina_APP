package com.jiyun.txl.kaiyuanchina_app.Fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.dongtan.MyDongTanFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.dongtan.NewsDongTanFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.dongtan.ReMenDongTanFragment;
import com.jiyun.txl.kaiyuanchina_app.MainActivity;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.DongTanAdapter;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述 动弹类
 */

public class TanYiTanFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> listName;
    private List<BaseFragment> listFragment;

    @Override
    protected int getlayout() {

        return R.layout.dongtan;
    }

    @Override
    protected void initView(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.dongtan_title);
        viewPager = (ViewPager) view.findViewById(R.id.dongtan_viewpager);
    }

    @Override
    protected void initData() {
        listName = new ArrayList<>();
        listFragment = new ArrayList<>();
        listName.add("最新动弹");
        listName.add("热门动弹");
        listName.add("我的动弹");

        listFragment.add(new NewsDongTanFragment());
        listFragment.add(new ReMenDongTanFragment());
        listFragment.add(new MyDongTanFragment());
        viewPager.setAdapter(new DongTanAdapter(getActivity().getSupportFragmentManager(), listName, listFragment));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void listener() {

    }

    @Override
    protected void updateActionBar() {
        super.updateActionBar();
        ((MainActivity) App.baseActivity).setTitle("动弹");
    }
}
