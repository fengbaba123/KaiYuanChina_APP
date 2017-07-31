package com.jiyun.txl.kaiyuanchina_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiyun.txl.kaiyuanchina_app.Activity.FaBiaoDongTanActivity;
import com.jiyun.txl.kaiyuanchina_app.Activity.LoginActivity;
import com.jiyun.txl.kaiyuanchina_app.Activity.SouSuoActivity;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Fragment.DiscouverFragment;
import com.jiyun.txl.kaiyuanchina_app.Fragment.MyFragment;
import com.jiyun.txl.kaiyuanchina_app.Fragment.TanYiTanFragment;
import com.jiyun.txl.kaiyuanchina_app.Fragment.ZongHeFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss.Retrofitimple;
import com.jiyun.txl.kaiyuanchina_app.config.FragmentBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private SharedPreferences sharedPreferences;
    @Bind(R.id.TitleBar)
    RelativeLayout TitleBar;
    @Bind(R.id.zhuye_content)
    FrameLayout zhuyeContent;
    @Bind(R.id.main_ZongHe)
    RadioButton mainZongHe;
    @Bind(R.id.main_DongTan)
    RadioButton mainDongTan;
    @Bind(R.id.main_FaXian)
    RadioButton mainFaXian;
    @Bind(R.id.main_WoDe)
    RadioButton mainWoDe;
    @Bind(R.id.Main_RadioGroup)
    RadioGroup MainRadioGroup;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.Main_Title)
    TextView MainTitle;
    @Bind(R.id.main_sousuo)
    ImageView mainSousuo;
    @Bind(R.id.main_Login)
    ImageView mainLogin;
    private FragmentManager manager;
    private boolean checkedMy = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        FragmentBuilder.getInstance().start(ZongHeFragment.class, R.id.zhuye_content)
                .builder();

    }

    @Override
    protected void initListener() {
        MainRadioGroup.setOnCheckedChangeListener(this);
        mainLogin.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);

                if(sharedPreferences.getString("uid","").isEmpty()){
                    Intent intent = new Intent(App.baseActivity, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(App.baseActivity, FaBiaoDongTanActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_ZongHe:
                FragmentBuilder.getInstance().start(ZongHeFragment.class, R.id.zhuye_content)
                        .builder();
                TitleBar.setVisibility(View.VISIBLE);
                break;
            case R.id.main_DongTan:
                TitleBar.setVisibility(View.VISIBLE);
                FragmentBuilder.getInstance().start(TanYiTanFragment.class, R.id.zhuye_content)
                        .builder();
                break;
            case R.id.main_FaXian:
                TitleBar.setVisibility(View.VISIBLE);
                FragmentBuilder.getInstance().start(DiscouverFragment.class, R.id.zhuye_content)
                        .builder();
                Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_WoDe:
                if (checkedMy) {
                    TitleBar.setVisibility(View.GONE);
                    FragmentBuilder.getInstance().start(MyFragment.class, R.id.zhuye_content)
                            .builder();
                }

                break;
        }
    }

    public void setTitle(String str) {
        MainTitle.setText(str);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentBuilder.clean();
        Retrofitimple.RetrofitimpleClean();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.main_sousuo)
    public void onViewClicked() {
        Intent intent = new Intent(this, SouSuoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        manager = getSupportFragmentManager();
        FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1);
        String name = entry.getName();

        if ("DiscouverFragment".equals(name)
                || "MyFragment".equals(name)
                || "ZongHeFragment".equals(name)
                || "TanYiTanFrament".equals(name)) {


//            finish();


        } else {
            if (manager.getBackStackEntryCount() > 1) {
                manager.popBackStackImmediate();
                FragmentManager.BackStackEntry entryback = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1);
                String nameback = entry.getName();
                App.lastFragment = (BaseFragment) manager.findFragmentByTag(nameback);
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private long mExitTime;
    public RadioGroup getMainRadioGroup() {
        return MainRadioGroup;
    }
//    public RelativeLayout getMainTitleBar() {
//        return MainTitleBar;
//    }

}
