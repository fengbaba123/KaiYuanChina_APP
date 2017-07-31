package com.jiyun.txl.kaiyuanchina_app.Utils;

import android.support.v7.widget.DividerItemDecoration;

import com.androidkun.PullToRefreshRecyclerView;
import com.jiyun.txl.kaiyuanchina_app.App;

/**
 * 类描述:
 */

public class PullViewRefash{
    public static void shuxing(PullToRefreshRecyclerView pullToRefreshRecyclerView){
        //是否开启下拉刷新功能
        pullToRefreshRecyclerView.setPullRefreshEnabled(true);
        //是否开启上拉加载功能
        pullToRefreshRecyclerView.setLoadingMoreEnabled(true);
        //是否显示上次刷新的时间
        pullToRefreshRecyclerView.displayLastRefreshTime(true);
        pullToRefreshRecyclerView.onRefresh();
        pullToRefreshRecyclerView.addItemDecoration(new DividerItemDecoration(App.baseActivity,DividerItemDecoration.VERTICAL));

    }

}
