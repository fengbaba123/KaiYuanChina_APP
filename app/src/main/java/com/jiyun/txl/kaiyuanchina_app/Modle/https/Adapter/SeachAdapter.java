package com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;

import java.util.List;

/**
 * 这个适配器可以用作任何的TabLayout 和 Viewpager 的适配器
 */

public class SeachAdapter extends FragmentStatePagerAdapter {
    private List<String> list;
    private List<BaseFragment> baseFragmentList;

    public SeachAdapter(FragmentManager fm, List<String> list, List<BaseFragment> baseFragmentList) {
        super(fm);
        this.list = list;
        this.baseFragmentList = baseFragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        return baseFragmentList.get(position);
    }

    @Override
    public int getCount() {
        if(baseFragmentList.size()==0){
            return 0;
        }
        return baseFragmentList.size();
    }

       @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
