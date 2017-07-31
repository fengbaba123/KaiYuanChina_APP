package com.jiyun.txl.kaiyuanchina_app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiyun.txl.kaiyuanchina_app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SecondActivity extends Activity {


    @Bind(R.id.by_time)
    RelativeLayout byTime;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.time_layout)
    LinearLayout timeLayout;
    @Bind(R.id.ll_pop)
    RelativeLayout llPop;
    private RelativeLayout mll_pop;
    private LinearLayout mll_time_layout;
    private boolean time_flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doghua_second);
        ButterKnife.bind(this);
        mll_pop = (RelativeLayout) findViewById(R.id.ll_pop);
        mll_time_layout = (LinearLayout) findViewById(R.id.time_layout);
    }

    public void by_time(View view) {
        mll_pop.setVisibility(View.VISIBLE);
        if (time_flag) {
            time_flag = !time_flag; // true
            TranslateAnimation ta = new TranslateAnimation(0, 0, 0, -94);
            ta.setDuration(500);
            ta.setFillAfter(true);
            mll_pop.startAnimation(ta);

        } else {
            if (!time_flag) { //true
                time_flag = !time_flag;
                mll_pop.setVisibility(View.VISIBLE);
                TranslateAnimation ta = new TranslateAnimation(0, 0, -211, 0);
                ta.setDuration(710);
                ta.setFillAfter(true);
                mll_pop.startAnimation(ta);
            } else {
                time_flag = !time_flag;
                TranslateAnimation ta = new TranslateAnimation(0, 0, 0, -211);
                ta.setDuration(710);
                ta.setFillAfter(true);
                mll_pop.startAnimation(ta);
            }

        }

    }

}
