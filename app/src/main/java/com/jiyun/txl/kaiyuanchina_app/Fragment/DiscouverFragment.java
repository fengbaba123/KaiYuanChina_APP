package com.jiyun.txl.kaiyuanchina_app.Fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiyun.txl.kaiyuanchina_app.Activity.KaiYuanActivity;
import com.jiyun.txl.kaiyuanchina_app.Activity.SSActivity;
import com.jiyun.txl.kaiyuanchina_app.Activity.YaoYiYaoActivity;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.MainActivity;
import com.jiyun.txl.kaiyuanchina_app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述 发现的Fragment
 */

public class DiscouverFragment extends BaseFragment {
    @Bind(R.id.dis_mytuijian)
    LinearLayout disMytuijian;
    @Bind(R.id.dis_kyrj)
    LinearLayout disKyrj;
    @Bind(R.id.ss)
    TextView sS;
    @Bind(R.id.dis_saoyisao)
    LinearLayout disSaoyisao;
    @Bind(R.id.dis_yaoyiyao)
    LinearLayout disYaoyiyao;
    @Bind(R.id._fujianchengxuyuan)
    LinearLayout Fujianchengxuyuan;
    @Bind(R.id.dis_xxhuodong)
    LinearLayout disXxhuodong;

    @Override
    protected int getlayout() {
        return R.layout.dicover_style;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void listener() {

    }

    @Override
    protected void updateActionBar() {
        super.updateActionBar();
        ((MainActivity) App.baseActivity).setTitle("发现");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.dis_mytuijian, R.id.dis_kyrj, R.id.dis_saoyisao, R.id.dis_yaoyiyao, R.id._fujianchengxuyuan, R.id.dis_xxhuodong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dis_mytuijian:
                break;
            case R.id.dis_kyrj:
                Intent intent = new Intent(getActivity().getApplicationContext(), KaiYuanActivity.class);
                startActivity(intent);
                break;
            case R.id.dis_saoyisao:
                startActivity(new Intent(App.baseActivity, SSActivity.class));

                break;
            case R.id.dis_yaoyiyao:
                Intent intent1 = new Intent(App.baseActivity, YaoYiYaoActivity.class);
                startActivity(intent1);
                break;
            case R.id._fujianchengxuyuan:
                break;
            case R.id.dis_xxhuodong:
//                Intent in = new Intent(App.baseActivity, XianXiaHuoDong.class);
//                startActivity(in);

                break;


        }
    }
}
