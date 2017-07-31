package com.jiyun.txl.kaiyuanchina_app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jiyun.txl.kaiyuanchina_app.Activity.EWMaActivity;
import com.jiyun.txl.kaiyuanchina_app.Activity.LoginActivity;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.GeRenINBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss.HttpFactory;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.Utils;
import com.thoughtworks.xstream.XStream;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述 我的Fragment
 */

public class MyFragment extends BaseFragment {
    @Bind(R.id.My_head)
    ImageView MyHead;
    @Bind(R.id.My_Name)
    TextView MyName;
    @Bind(R.id.My_shezhi)
    ImageView MyShezhi;
    @Bind(R.id.My_jifen)
    TextView MyJifen;
    @Bind(R.id.My_DOngtanSize)
    TextView MyDOngtanSize;
    @Bind(R.id.My_Dongtan)
    RelativeLayout MyDongtan;
    @Bind(R.id.My_shoucangSize)
    TextView MyShoucangSize;
    @Bind(R.id.My_shoucang)
    RelativeLayout MyShoucang;
    @Bind(R.id.My_guanzhuSize)
    TextView MyGuanzhuSize;
    @Bind(R.id.My_guanzhu)
    RelativeLayout MyGuanzhu;
    @Bind(R.id.My_fengsiSize)
    TextView MyFengsiSize;
    @Bind(R.id.My_fengsi)
    RelativeLayout MyFengsi;
    @Bind(R.id.ewma)
    ImageView ewMa;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;

    @Override
    protected int getlayout() {
        return R.layout.my_style;
    }

    @Override
    protected void initView(View view) {
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
    }

    @Override
    protected void initData() {
        getUserm();
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
    protected void onShow() {
        super.onShow();
        getUserm();

    }

    private void getUserm() {
        //获取uid
        String uid = sharedPreferences.getString("uid", "");
        //判断uid是否为空
        if (!uid.isEmpty()) {
            Map<String, String> map = new HashMap<>();
            map.put("uid", uid);
            HttpFactory.getFactory().Get(Utils.LOGIN_MESSAGE, map, new MyCallback() {
                @Override
                public void successful(String body) {
//                    Log.d("MyFragment", body);
                    XStream xStream = new XStream();
                    xStream.alias("oschina", GeRenINBean.class);
                    GeRenINBean geRenXinXiBean = (GeRenINBean) xStream.fromXML(body);
                    MyName.setText(geRenXinXiBean.getUser().getName());
                    Glide.with(App.baseActivity).load(geRenXinXiBean.getUser().getPortrait())
                            .asBitmap()
                            .centerCrop()
                            .into(new BitmapImageViewTarget(MyHead) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(App.baseActivity.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    MyHead.setImageDrawable(circularBitmapDrawable);
                                }
                            });


                }

                @Override
                public void failure(String errorMessage) {

                }
            });
        } else {
            MyName.setText("请登录");
            MyHead.setImageResource(R.mipmap.ic_launcher);

        }
    }



    @OnClick({R.id.My_Dongtan, R.id.My_shoucang, R.id.My_guanzhu, R.id.My_fengsi,R.id.My_shezhi, R.id.My_head,R.id.ewma})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.My_Dongtan:
                break;
            case R.id.My_shoucang:
                break;
            case R.id.My_guanzhu:
                break;
            case R.id.My_fengsi:
                break;
            case R.id.My_shezhi:
                edit.remove("uid");
                edit.commit();
                Toast.makeText(App.baseActivity, "注销成功", Toast.LENGTH_SHORT).show();
                getUserm();
                break;
            case R.id.My_head:
                if (sharedPreferences.getString("uid", "").isEmpty()) {
                    Intent intent = new Intent(App.baseActivity, LoginActivity.class);
//                    startActivity(intent);
                    startActivityForResult(intent,3);
                } else {
                    Toast.makeText(App.baseActivity, "你好", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ewma:
                startActivity(new Intent(App.baseActivity, EWMaActivity.class));
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3&&resultCode==3){
            getUserm();
        }
    }
}
