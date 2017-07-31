package com.jiyun.txl.kaiyuanchina_app.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.TuiJianBoKeFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.zonghe.ReMenZiXunFragment;
import com.jiyun.txl.kaiyuanchina_app.MainActivity;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类描述 综合页面
 */

public class ZongHeFragment extends BaseFragment implements MyGridView.onClickListener {

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
    private List<String> listName = new ArrayList<>();
    private List<BaseFragment> listBaseFragment = new ArrayList<>();
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
        my_grid_ll = (LinearLayout) view.findViewById(R.id.my_grid_ll);
        init();
       mInitData();
    }
    private void init() {
        mList1 = new ArrayList<>();
        mList2 = new ArrayList<>();
        myGridView1 = (MyGridView) App.baseActivity.findViewById(R.id.dragable_myGridLayout);
        myGridView2 = (MyGridView) App.baseActivity.findViewById(R.id.dis_dragable_myGridLayout);
        myGridView2.setListener(this);
        myGridView1.setListener(this);

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
                //这是隐藏TabLayout当点击加号的时候
                homeNewsTablayout.setVisibility(View.GONE);
                if (mFlag) {// ��
                    myGridRlTitle.setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(getContext(),
                            R.anim.shunshizhen_raotate);
                    view.startAnimation(anim);

                    // ����ҳ��
                    Animation mainOutAnim = AnimationUtils.loadAnimation(
                            getContext(), R.anim.top2bottom);
                    my_grid_ll.startAnimation(mainOutAnim);

                    // �����ɰ�͸����Ϊ��ȫ��͸��
                    AlphaAnimation aa = new AlphaAnimation(0f, 1f);
                    aa.setDuration(500);
                    aa.setFillAfter(true);
                    myGridRlTitle.startAnimation(aa);
                    if(App.baseActivity instanceof MainActivity) {
                        ((MainActivity)App.baseActivity).getMainRadioGroup().setVisibility(View.GONE);
                    }
                    mFlag = false;
                } else {// �ر�
                    Animation anim = AnimationUtils.loadAnimation(getContext(),
                            R.anim.nishizhen_rotate);
                    view.startAnimation(anim);

                    // �ر���ҳ��
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
                        ((MainActivity)App.baseActivity).getMainRadioGroup().setVisibility(View.VISIBLE);
                    }
                    mFlag = true;
                }
            }
        });
    }
    @Override
    protected void initData() {
        listName.add("开源资讯");
        listName.add("推荐博客");
        listName.add("热门资讯");
        listName.add("每日一博");
        listName.add("云计算");
        listName.add("软件工程");
        listName.add("最新翻译");
        listBaseFragment.add(new ZongHeHomeFragment());

        listBaseFragment.add(new TuiJianBoKeFragment());
        listBaseFragment.add(new ReMenZiXunFragment());
        listBaseFragment.add(new TuiJianBoKeFragment());
        listBaseFragment.add(new TuiJianBoKeFragment());
        listBaseFragment.add(new TuiJianBoKeFragment());
        listBaseFragment.add(new TuiJianBoKeFragment());
        homeNewsTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        homeNewsViewpager.setAdapter(new MyZOngHeAdapter(getActivity().getSupportFragmentManager()));

        homeNewsTablayout.setupWithViewPager(homeNewsViewpager);
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

    @Override
    public void setOnItemClickListener(View view, int Position) {
        //        myGridView2.removeView(view);
        myGridView2.remove(view);

        String str = mList2.get(Position);
        //这是向外提供的方法进行添加的
        myGridView1.add(str);

        //这是调用这个方法直接进行添加的，
//        myGridView1.addItem(str);
    }

    class MyZOngHeAdapter extends FragmentStatePagerAdapter {

        public MyZOngHeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listBaseFragment.get(position);
        }

        @Override
        public int getCount() {
            return listBaseFragment.isEmpty() ? 0 : listBaseFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return listName.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.zonghe_add:
//
//                Toast.makeText(getActivity().getApplicationContext(), "点击", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
//
//    private void getmph(View view) {
////第一个参数fromDegrees为动画起始时的旋转角度
////第二个参数toDegrees为动画旋转到的角度
////第三个参数pivotXType为动画在X轴相对于物件位置类型
////第四个参数pivotXValue为动画相对于物件的X坐标的开始位置
////第五个参数pivotXType为动画在Y轴相对于物件位置类型
////第六个参数pivotYValue为动画相对于物件的Y坐标的开始位置
//        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, +180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setDuration(3000);
//        view.setAnimation(rotateAnimation);
//    }

    @Override
    protected void updateActionBar() {
        super.updateActionBar();
        ((MainActivity) App.baseActivity).setTitle("综合");
//        new MainActivity().setTitle("综合");
//        if(App.baseActivity instanceof MainActivity){
//            ((MainActivity) App.baseActivity).getMainTitleBar().setVisibility(View.VISIBLE);
//
//        }
    }
}
