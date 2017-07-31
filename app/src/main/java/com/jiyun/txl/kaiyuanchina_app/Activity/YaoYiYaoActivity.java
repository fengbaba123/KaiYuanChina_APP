package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.YaoYiYaoBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss.HttpFactory;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.Keys;
import com.jiyun.txl.kaiyuanchina_app.Utils.Utils;
import com.thoughtworks.xstream.XStream;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述:摇一摇
 */

public class YaoYiYaoActivity extends BaseActivity implements SensorEventListener {

    @Bind(R.id.yaoyiyao_text)
    TextView yaoyiyaoText;
    @Bind(R.id.yaoyiyao_head)
    ImageView yaoyiyaoHead;
    @Bind(R.id.yaoyiyao_title)
    TextView yaoyiyaoTitle;
    @Bind(R.id.yaoyiyao_lin)
    LinearLayout yaoyiyaoLin;
    private SensorManager sensorManager;
    private Sensor defaultSensor;
    private boolean isShake = false;
    private Vibrator mVibrator;
    private SoundPool mSoundPool;
    private int load;
    private YaoYiYaoBean yaoYiYaoBean;

    @Override
    protected int getLayout() {
        return R.layout.activity_yaoyiyao;
    }

    @Override
    protected void initView() {
        mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        load = mSoundPool.load(this, R.raw.shake, 1);
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            //获取加速度传感器
            defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (defaultSensor != null) {
                sensorManager.registerListener((SensorEventListener) this, defaultSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    protected void onPause() {
        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        if (sensorManager != null) {
                         //注销监听器
            sensorManager.unregisterListener(this);
        }
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17) && !isShake) {
                isShake = true;
                // TODO: 2016/10/19 实现摇动逻辑, 摇动后进行震动
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Log.d("摇动", "onSensorChanged: 摇动");
                        handler.sendEmptyMessage(1);
                        handler.sendEmptyMessage(2);
                    }
                };
                thread.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    YaoYiYaoActivity.this.mVibrator.vibrate(300);


                    break;
                case 2:
                    HttpFactory.getFactory().Get(Utils.YAOYIYAO, new HashMap<String, String>(), new MyCallback() {
                        @Override
                        public void successful(String body) {
//                            Log.e("摇一摇", body);
                            XStream xStream = new XStream();
                            xStream.alias("oschina", YaoYiYaoBean.class);
                            yaoYiYaoBean = (YaoYiYaoBean) xStream.fromXML(body);
                            Glide.with(YaoYiYaoActivity.this).load(yaoYiYaoBean.getImage()).into(yaoyiyaoHead);
                            yaoyiyaoTitle.setText(yaoYiYaoBean.getDetail());
                            YaoYiYaoActivity.this.mSoundPool.play(YaoYiYaoActivity.this.load, 1, 1, 0, 0, 1);
                            isShake = false;

                        }

                        @Override
                        public void failure(String errorMessage) {

                        }
                    });
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.yaoyiyao_lin)
    public void onViewClicked() {
        if (TextUtils.isEmpty(yaoyiyaoTitle.getText())) {
            Toast.makeText(this, "请先摇一摇", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(YaoYiYaoActivity.this, WebActivity.class);
        intent.putExtra(Keys.WEB_URL, yaoYiYaoBean.getUrl());
        intent.putExtra(Keys.WEB_NAME, "摇一摇");
        startActivity(intent);


    }
}
