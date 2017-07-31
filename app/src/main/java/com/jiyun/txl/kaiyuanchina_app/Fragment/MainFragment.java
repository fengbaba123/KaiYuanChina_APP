package com.jiyun.txl.kaiyuanchina_app.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.MainActivity;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter.MyAdapter;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainFragment extends BaseFragment implements MyGridView.onClickListener{


    @Bind(R.id.home_news_viewpager)
    ViewPager homeNewsViewpager;
    @Bind(R.id.ll_2222)
    LinearLayout ll2222;
    @Bind(R.id.dragable_myGridLayout)
    MyGridView dragableMyGridLayout;
    @Bind(R.id.dis_dragable_myGridLayout)
    MyGridView disDragableMyGridLayout;
    @Bind(R.id.home_news_tablayout)
    TabLayout homeNewsTablayout;
    @Bind(R.id.tv_delete_sort)
    TextView tvDeleteSort;
    @Bind(R.id.myGrid_rl_title)
    RelativeLayout myGridRlTitle;
    @Bind(R.id.home_news_imgview)
    ImageView homeNewsImgview;
    private List<String> mListName;
    private List<Fragment> mList;
    private MyAdapter adapter;
    private boolean mFlag = true;
    private LinearLayout my_grid_ll;
    private MyGridView myGridView1;
    private MyGridView myGridView2;
    private List<String> mList1;
    private List<String> mList2;

    @Override
    protected int getlayout() {
        return R.layout.activity_donghua_main;
    }

    @Override
    protected void initView(View view) {
        mListName = new ArrayList<>();
        mList = new ArrayList<>();
        my_grid_ll = (LinearLayout) view.findViewById(R.id.my_grid_ll);

    }
    protected void init(){
        mList1 = new ArrayList<>();
        mList2 = new ArrayList<>();
        myGridView1 = (MyGridView) App.baseActivity.findViewById(R.id.dragable_myGridLayout);
        myGridView2 = (MyGridView) App.baseActivity.findViewById(R.id.dis_dragable_myGridLayout);
        myGridView2.setListener(this);
        myGridView1.setListener(this);
    }

    @Override
    protected void initData() {
init();
    }

    @Override
    protected void listener() {

    }
    private void mInitData() {
        myGridView1.setBoo(true);
        mList1 = new ArrayList<>();
        mList1.add("热门资讯");
        mList1.add("开源资讯");
        mList1.add("推荐博客");
        mList1.add("技术问题");
        mList1.add("移动开发");
        mList1.add("云计算");
        mList1.add("软件工程");
        mList1.add("开源软件");
        myGridView1.setmList(mList1);


        myGridView2.setBoo(false);
        mList2 = new ArrayList<>();
        mList2.add("数据库");
        mList2.add("编程语言");
        mList2.add("游戏开发");
        mList2.add("职业生涯");
        mList2.add("最新博客");
        mList2.add("开源访谈");
        mList2.add("高手问答");
        myGridView2.setmList(mList2);

        homeNewsImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_grid_ll.setVisibility(View.VISIBLE);
                homeNewsTablayout.setVisibility(View.GONE);
                if (mFlag) {
                    myGridRlTitle.setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(getContext(),
                            R.anim.shunshizhen_raotate);
                    view.startAnimation(anim);
                    Animation mainOutAnim = AnimationUtils.loadAnimation(
                            getContext(), R.anim.top2bottom);
                    my_grid_ll.startAnimation(mainOutAnim);

                    AlphaAnimation aa = new AlphaAnimation(0f, 1f);
                    aa.setDuration(500);
                    aa.setFillAfter(true);
                    myGridRlTitle.startAnimation(aa);
                    if(App.baseActivity instanceof MainActivity) {
//                        ((MainActivity)App.baseActivity).getMainRadioGroup().setVisibility(View.GONE);
                    }
                    mFlag = false;
                } else {
                    Animation anim = AnimationUtils.loadAnimation(getContext(),
                            R.anim.nishizhen_rotate);
                    view.startAnimation(anim);

                    Animation mainOutAnim = AnimationUtils.loadAnimation(
                            getContext(), R.anim.bottom2top);
                    my_grid_ll.startAnimation(mainOutAnim);

                    AlphaAnimation aa = new AlphaAnimation(1.0f, 0f);
                    aa.setDuration(500);
                    aa.setFillAfter(true);
                    myGridRlTitle.startAnimation(aa);

                    myGridRlTitle.setVisibility(View.GONE);
                    //当在进行点击返回的时候，让这个Tab显示
                    homeNewsTablayout.setVisibility(View.VISIBLE);

                    if(App.baseActivity instanceof MainActivity) {
//                        ((MainActivity)App.baseActivity).getMainRadioGroup().setVisibility(View.VISIBLE);
                    }
                    mFlag = true;
                }
            }
        });
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

    @Override
    public void setOnItemClickListener(View view, int Position) {

    }
}
