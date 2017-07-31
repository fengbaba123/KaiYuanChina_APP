package com.jiyun.txl.kaiyuanchina_app.HomeFragment.kaiyuanruanjian;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.FirstField;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 这个是开源软件 分类
 */

public class FengLeiFragment extends BaseFragment {
    @Bind(R.id.fenlei_frame)
    FrameLayout fenleiFrame;
    private List<FirstField.SoftwareTypeBean> list;
    public static FragmentManager fragmentManager;

    @Override
    protected int getlayout() {
        return R.layout.fragment_fenlei;
    }

    @Override
    protected void initView(View view) {

    }


    @Override
    protected void initData() {
        //默认显示一级页面
        fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fenlei_frame,new FirstLeiFragment(),"一级");
//        fragmentTransaction.show(new FirstLeiFragment());
        fragmentTransaction.commit();
    }



    @Override
    protected void listener() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


}
