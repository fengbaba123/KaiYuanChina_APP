package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.sousuo.SouSuoJieGuoFragment;
import com.jiyun.txl.kaiyuanchina_app.HomeFragment.sousuo.SouSuoListFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.DB.MyManager;
import com.jiyun.txl.kaiyuanchina_app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索页面
 */

public class SouSuoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.sousuo_cleartv)
    ImageView sousuoCleartv;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Bind(R.id.sousuo_edit)
    EditText sousuoEdit;
    @Bind(R.id.sousuo_queding)
    TextView sousuoQueding;
    @Bind(R.id.souuso_bar)
    LinearLayout souusoBar;
    @Bind(R.id.sousuo_fragme)
    FrameLayout sousuoFragme;
    //数据库管理
    private MyManager myManger;
    //Fragment的管理器
    private FragmentManager manager;
    private boolean boo;

    @Override
    protected int getLayout() {
        return R.layout.activity_sousuo;
    }

    @Override
    protected void initView() {
        //默然图片X号影藏
        sousuoCleartv.setVisibility(View.GONE);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        myManger = new MyManager(this);
        manager = this.getSupportFragmentManager();
        sousuoQueding.setOnClickListener(this);

        //  这里显示往日搜索的Fragment
        initList();

    }

    //监听
    @Override
    protected void initListener() {
        //这个是输入字后那个 X  号的监听
        sousuoCleartv.setOnClickListener(this);
        //这个是搜索框发生变化的时候的监听 Edittext

        sousuoEdit.addTextChangedListener(new TextWatcher() {
            //输入文本之前的状态
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //输入文本之中的状态
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //这个是输入完成后的监听
            @Override
            public void afterTextChanged(Editable s) {
                //判断字符是否大于0 大于0代表有内容
                if (sousuoEdit.length() > 0) {
                    //有内容 让 X 号显示
                    sousuoQueding.setText("搜索");
                    sousuoCleartv.setVisibility(View.VISIBLE);
                } else {
                    //没有内容 让 X 号消失
                    sousuoQueding.setText("取消");

                    sousuoCleartv.setVisibility(View.GONE);
                }
                if (sousuoEdit.length() == 0) {
                    initList();
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //这个是点击搜索后的监听
    @OnClick(R.id.sousuo_queding)
    public void onViewClicked() {
        if (sousuoQueding.getText().toString().equals("取消")){

                    finish();


        }else if (sousuoQueding.getText().toString().equals("搜索")){
        //判断文本框是否为空
        if (TextUtils.isEmpty(sousuoEdit.getText())) {
            Toast.makeText(this, "请输入搜索的内容", Toast.LENGTH_SHORT).show();
        } else {
            //添加到数据库
            boolean insert = myManger.insert(sousuoEdit.getText().toString());
            //通过shaare 传值
            editor.putString("sousuo", sousuoEdit.getText().toString());
            editor.commit();
            //这里显示出结果的Fragment
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.sousuo_fragme, new SouSuoJieGuoFragment());
            fragmentTransaction.commit();
        }
        }
    }

    //点击 X 号后的监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sousuo_cleartv:
                //使文本框为空
                sousuoEdit.setText("");
                //这里显示往日搜索的Fragment
                initList();
                break;
        }
    }

    /**
     * 这里显示往日搜索的Fragment
     */
    private void initList() {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.sousuo_fragme, new SouSuoListFragment());
        fragmentTransaction.commit();
    }

    public void setEd(String str) {
        sousuoEdit.setText(str);
    }

}
