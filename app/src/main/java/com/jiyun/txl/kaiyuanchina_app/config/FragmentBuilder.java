package com.jiyun.txl.kaiyuanchina_app.config;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;

import static com.jiyun.txl.kaiyuanchina_app.App.lastFragment;

/**
 * 类描述  用于显示Fragment
 */

public class  FragmentBuilder {

    private FragmentManager fragmentManager;
    private BaseFragment fragment;
    private FragmentTransaction fragmentTransaction;
    private String simpleName;

    private FragmentBuilder() {
        init();
    }

    private static FragmentBuilder fragmentBuilder;

    public static synchronized FragmentBuilder getInstance() {
        if (fragmentBuilder == null) {
            fragmentBuilder = new FragmentBuilder();
        }
        return fragmentBuilder;
    }

    private void init() {
        fragmentManager = App.baseActivity.getSupportFragmentManager();

    }

    public FragmentBuilder start(Class<? extends BaseFragment> Baseclass, int ViewID) {
        fragmentTransaction = fragmentManager.beginTransaction();
        simpleName = Baseclass.getSimpleName();
        fragment = (BaseFragment) fragmentManager.findFragmentByTag(simpleName);
        if (fragment == null) {
            try {
                fragment = Baseclass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fragmentTransaction.add(ViewID, fragment, simpleName);
        }
        if (lastFragment != null) {
            fragmentTransaction.hide(lastFragment);
        }
        fragmentTransaction.show(fragment);
        fragmentTransaction.addToBackStack(simpleName);
        return this;
    }

    public FragmentBuilder setParms(Bundle bundle) {
        fragment.setParmars(bundle);
        return this;
    }


    public BaseFragment builder() {
        lastFragment = fragment;

        fragmentTransaction.commit();
        return fragment;
    }

    public static void clean() {
        fragmentBuilder = null;
    }
}
