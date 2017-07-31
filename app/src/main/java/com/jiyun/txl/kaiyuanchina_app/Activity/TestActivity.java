package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;



public class TestActivity extends BaseActivity {


    @Bind(R.id.test_viewpager)
    ViewPager testViewpager;
    private List<View> list = new ArrayList<>();
    private ImageView imageView;
    private int currentItem = 1000000;
    private final int CODE_start = 1;
    private final int CODE_STOP = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_start:
                    testViewpager.setCurrentItem(currentItem++);
                    handler.sendEmptyMessageDelayed(CODE_start, 2000);
                    break;
                case CODE_STOP:
                    handler.removeMessages(CODE_start);
                    break;
            }
        }
    };



    @Override
    protected int getLayout() {
        return R.layout.test_listview;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        testViewpager.setAdapter(new Myadapter(list));
        testViewpager.setCurrentItem(currentItem++);
        handler.sendEmptyMessageDelayed(CODE_start, 2000);
        testViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("第一个方法", "s");
            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                Log.e("第二个", "s");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("第三个", "s");
            }
        });
    }


    protected void loadData() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_test1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.item_test2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.item_test3, null);

        list.add(view);
        list.add(view2);
        list.add(view3);

    }


    class Myadapter extends PagerAdapter {
        private List<View> list;

        public Myadapter(List<View> list) {
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

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (container != null) {
                container.removeView(list.get(position % list.size()));
            }
            container.addView(list.get(position % list.size()));
            return list.get(position % list.size());
        }
    }


}
