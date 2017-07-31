package com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lenovo on 2017/5/27.
 */

public class MyAdapter extends PagerAdapter {
    private List<View> list;

    public MyAdapter(List<View> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    /**一定要写destory方法  里面不做任何操作
     * 这里执行删除view 和添加view方法
     *
     * * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (container != null) {
            container.removeView(list.get(position % list.size()));
        }
        container.addView(list.get(position % list.size()));
        return list.get(position % list.size());
    }
}
