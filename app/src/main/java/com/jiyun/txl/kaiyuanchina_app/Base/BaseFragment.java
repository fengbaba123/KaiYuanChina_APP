package com.jiyun.txl.kaiyuanchina_app.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 类描述 封装Fragment
 */

public abstract class BaseFragment extends Fragment {
    private boolean boo = true;
    public    Bundle bundle;
    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bundle = savedInstanceState;
        return inflater.inflate(getlayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
        listener();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(boo){
            initData();
            updateActionBar();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        boo = false;
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    protected abstract int getlayout();

    protected abstract void initView(View view);

    protected abstract void initData();

    protected abstract void listener();
    public void setParmars(Bundle bundle) {

//        this.bundle = bundle;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.d("BaseFragment", "onHiddenchanged");
        if (hidden) {
            onHidden();
        } else {
            onShow();
        }
    }

    protected void updateActionBar() {

    }



    protected void onShow() {
        updateActionBar();
    }

    protected void onHidden() {
    }
}
