package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jiyun.txl.kaiyuanchina_app.Base.BaseActivity;
import com.jiyun.txl.kaiyuanchina_app.MainActivity;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.LoginBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss.ILogin;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.Utils;
import com.thoughtworks.xstream.XStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.finsh_imang)
    ImageView finshImang;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Bind(R.id.login_username)
    EditText loginUsername;
    @Bind(R.id.login_pwd)
    EditText loginPwd;
    @Bind(R.id.login_Btn)
    Button loginBtn;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    protected void initListener() {
        loginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (loginUsername.length() > 0 && loginPwd.length() > 0) {
                    loginBtn.setEnabled(true);
                } else {
                    loginBtn.setEnabled(false);
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

    @OnClick(R.id.login_Btn)
    public void onViewClicked() {

        ILogin.login(Utils.LOGIN, loginUsername.getText().toString(), loginPwd.getText().toString()
                , new MyCallback() {
                    @Override
                    public void successful(String body) {
                        XStream xstream = new XStream();
                        xstream.alias("oschina", LoginBean.class);
                        LoginBean logBean = (LoginBean) xstream.fromXML(body);
                        if (logBean.getResult().getErrorCode().equals("1")) {
                            editor.putString("uid", logBean.getUser().getUid());
                            editor.commit();
                            Intent intent = new Intent();
                            setResult(3, intent);
                            finish();

                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        } else if (logBean.getResult().getErrorCode().equals("0")) {
                            Toast.makeText(LoginActivity.this, logBean.getResult().getErrorMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(String errorMessage) {

                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finsh_imang:
                Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
