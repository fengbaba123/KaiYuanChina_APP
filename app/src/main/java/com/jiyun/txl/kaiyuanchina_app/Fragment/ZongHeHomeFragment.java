package com.jiyun.txl.kaiyuanchina_app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.MyZiXunAdapter;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.InformetionBean;
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
 * 综合页面的资讯
 */

public class ZongHeHomeFragment extends BaseFragment implements PullToRefreshListener {
    ViewPager zixunLunbo;
    private int index;
    @Bind(R.id.zixun_PullView)
    PullToRefreshRecyclerView mKaiyuanzixunPullView;
    private List<InformetionBean.NewsBean> lsitNewsBean = new ArrayList<>();
    private List<View> list = new ArrayList<>();
    private int currentItem = 1000000;
    private final int CODE_start = 1;
    private final int CODE_STOP = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_start:
                    zixunLunbo.setCurrentItem(currentItem++);
                    handler.sendEmptyMessageDelayed(CODE_start, 2000);
                    break;
                case CODE_STOP:
                    handler.removeMessages(CODE_start);
                    break;
            }
        }
    };
    private View view1;
    private MyZiXunAdapter myZiXunAdapter;

    @Override
    protected int getlayout() {
        return R.layout.zixun_lunbo;
    }

    @Override
    protected void initView(View view) {
        view1 = LayoutInflater.from(App.baseActivity).inflate(R.layout.head_lunbo, null);
        zixunLunbo = (ViewPager) view1.findViewById(R.id.zixun_lunbo);
        PullViewRefash.shuxing(mKaiyuanzixunPullView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        mKaiyuanzixunPullView.setLayoutManager(manager);
        mKaiyuanzixunPullView.setPullToRefreshListener(this);

        myZiXunAdapter = new MyZiXunAdapter(
                getActivity().getBaseContext(), R.layout.item_style, lsitNewsBean);
        mKaiyuanzixunPullView.setAdapter(myZiXunAdapter);
        addLunBo();


    }

//

    @Override
    protected void initData() {
        jiaZaiShuJu();
        showLunBo();
        mKaiyuanzixunPullView.addHeaderView(view1);

    }

    private void jiaZaiShuJu() {
        Map<String, String> map = new HashMap<>();
        map.put("catalog", "1");
        map.put("pageIndex", String.valueOf(index));
        map.put("pageSize", "20");
        //网络请求
        HttpFactory.getFactory().Get(Utils.NEWS_InFORMETION, map, new MyCallback() {
            @Override
            public void successful(String body) {
                //开始解析
//                Log.e("ZongHeHomeFragmentZongHeHomeFragment",body);
                Log.e("ZOngheFragment", body);
                XStream xStream = new XStream();
                xStream.alias("oschina", InformetionBean.class);
                xStream.alias("news", InformetionBean.NewsBean.class);
//                xStream.alias("", InformetionBean.NewsBean.NewstypeBean.class);
                InformetionBean informetion = (InformetionBean) xStream.fromXML(body);
                //添加到集合 并且显示出来


                lsitNewsBean.addAll(informetion.getNewslist());
                myZiXunAdapter.notifyDataSetChanged();
                mKaiyuanzixunPullView.setRefreshComplete();

            }

            @Override
            public void failure(String errorMessage) {
                Log.d("ZongHeHomeFragment", errorMessage);
                mKaiyuanzixunPullView.setRefreshFail();
            }
        });
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

    private void showLunBo() {
       //添加适配器
        zixunLunbo.setAdapter(new MyLunBoadapter(list));
        //手动设置需要显示的Viewpager
        zixunLunbo.setCurrentItem(currentItem++);
//Viewpager的监听 滑动监听
        zixunLunbo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //手触摸是调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("第一个方法", "s");
//                handler.sendEmptyMessage(CODE_STOP);
            }
//切换页面调用
            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                Log.e("第二个", "s");
            }
//当下一个页面显示时调用
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("第三个", "s");
//                handler.sendEmptyMessageDelayed(CODE_start,2000);
            }
        });
    }

    private void addLunBo() {
        View view = LayoutInflater.from(App.baseActivity).inflate(R.layout.item_test1, null);
        View view2 = LayoutInflater.from(App.baseActivity).inflate(R.layout.item_test2, null);
        View view3 = LayoutInflater.from(App.baseActivity).inflate(R.layout.item_test3, null);

        list.add(view);
        list.add(view2);
        list.add(view3);

    }

    @Override
    public void onRefresh() {
        index = 0;
        mKaiyuanzixunPullView.post(new Runnable() {
            @Override
            public void run() {
                lsitNewsBean.clear();
                jiaZaiShuJu();


            }
        });
    }

    @Override
    public void onLoadMore() {

        mKaiyuanzixunPullView.postDelayed(new Runnable() {
            @Override
            public void run() {
                index++;
                jiaZaiShuJu();
                mKaiyuanzixunPullView.setLoadMoreComplete();

            }
        }, 2000);
    }

    class MyLunBoadapter extends PagerAdapter {
        private List<View> list;

        public MyLunBoadapter(List<View> list) {
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

//页面显示时调用 第一次显示先为true 后为false   所以再加一个bpplean
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //显示启动线程
            handler.sendEmptyMessageDelayed(CODE_start, 2000);
        } else {
            if (aBoolean) {
                aBoolean = false;
            } else {
                //影藏关闭线程
                handler.sendEmptyMessage(CODE_STOP);
            }
        }
    }

    private boolean aBoolean = true;
//当程序关闭时 关掉线程
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.sendEmptyMessage(CODE_STOP);
    }
}
