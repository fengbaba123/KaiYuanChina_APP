package com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 类描述:  登陆的Http
 */

public class ILogin {
    public static void login(String url, String userName, String pwd, final MyCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com/")
                .build();
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("pwd", pwd);
        map.put("keep_login", "1");


        IRetrofit iHttp = retrofit.create(IRetrofit.class);
        Call<ResponseBody> call = iHttp.Post(null,url, map);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                savecooike(response);
                try {
                    callback.successful(response.body().string());
                } catch (IOException e) {


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    private static void savecooike(Response<ResponseBody> response) {
        String cookie = "";
        Headers headers = response.headers();
        Set<String> names = headers.names();
        for (String name : names) {
            String key = name;
//            List<String> values = headers.values(key);
            String values = headers.get(key);

            if (key.contains("Set-Cookie"))
                cookie += values + ";";
        }

        if (cookie.length() > 0) {
            cookie = cookie.substring(0, cookie.length() - 1);
        }
        SharedPreferences sha = App.baseActivity.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sha.edit();
        edit.putString("cookie",cookie);
        edit.commit();
    }

}
