package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss.HttpFactory;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.Utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述:发表动弹
 */

public class FaBiaoDongTanActivity extends BaseActivity {
    @Bind(R.id.action_bar)
    TextView actionBar;
    @Bind(R.id.fabiaodongtan_Fasong)
    TextView fabiaodongtanFasong;
    @Bind(R.id.fabiaodongtan_ed)
    EditText fabiaodongtanEd;
    @Bind(R.id.fabiaodongtan_xuanze)
    Button fabiaodongtanXuanze;
    @Bind(R.id.fabiaodongtan_img)
    ImageView fabiaodongtanImg;
    private SharedPreferences sharedPreferences;
    private Dialog dialog;
    private File file1;
    private boolean boo;

    @Override
    protected int getLayout() {

        return R.layout.activity_fabiaodongtan;
    }

    @Override
    protected void initView() {
        boo = true;
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        actionBar.setText("弹一弹");
        file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg");
    }

    @Override
    protected void initListener() {
        fabiaodongtanXuanze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
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

    @OnClick(R.id.fabiaodongtan_Fasong)
    public void onViewClicked() {
        if (TextUtils.isEmpty(fabiaodongtanEd.getText())) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "正在发表", Toast.LENGTH_SHORT).show();
            Map<String, String> map = new HashMap<>();
            map.put("uid", sharedPreferences.getString("uid", ""));
            try {
                map.put("msg", new String(fabiaodongtanEd.getText().toString().trim().getBytes(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            showDiaLog();
            if (boo) {
                HttpFactory.getFactory().Post(Utils.FABIAO_DONGTAN, map, new MyCallback() {
                    @Override
                    public void successful(String body) {
                        Toast.makeText(FaBiaoDongTanActivity.this, "发表成功文字", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }
                    @Override
                    public void failure(String errorMessage) {
                        Toast.makeText(FaBiaoDongTanActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                HttpFactory.getFactory().Filed(Utils.FABIAO_DONGTAN, "img", file1, map, new MyCallback() {
                    @Override
                    public void successful(String body) {
                        Log.e("发表动弹body", body);


                        Toast.makeText(FaBiaoDongTanActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void failure(String errorMessage) {
                        Log.e("异常刚", errorMessage);
                        Toast.makeText(FaBiaoDongTanActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data != null) {
                cope(data.getData());
                boo = false;
            }

        }
        if (requestCode == 33) {
//             Log.d("FaBiaoDongTanActivity", data.getStringExtra("out")+"");
//            Log.e("kankan",data.getPackage()+"");
            boo = false;
            Log.e("kankna", file1.toString());
            Bitmap bitmap = BitmapFactory.decodeFile(file1.toString());
            fabiaodongtanImg.setImageBitmap(bitmap);

//            Log.e("url路径",uri.toString());
        }

    }

    private void showDiaLog() {
        View view = LayoutInflater.from(FaBiaoDongTanActivity.this).inflate(R.layout.progrss_dialog, null);
        dialog = new AlertDialog.Builder(FaBiaoDongTanActivity.this)
                .setView(view)
                .create();
        dialog.show();
    }

    private void cope(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("output", Uri.fromFile(file1));
        startActivityForResult(intent, 33);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        boo = true;
    }
}
