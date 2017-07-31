package com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss;


import android.content.Context;
import android.content.SharedPreferences;

import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class OkHttpUtils implements IHttp{
private SharedPreferences sharedPreferences;
    private static OkHttpUtils okHttpClass = new OkHttpUtils();

    private OkHttpUtils() {
        sharedPreferences = App.baseActivity.getSharedPreferences("data", Context.MODE_PRIVATE);

    }

    public static OkHttpUtils getOkHttp() {
        return okHttpClass;
    }


    /**
     * post请求
     * @param url
     * @param param
     * @param myCallBack
     */
    public void Post(String url, Map<String, String> param, final MyCallback myCallBack) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        FormBody.Builder formBody=new FormBody.Builder();
        if (param != null && param.size() > 0) {
            Set<String> set = param.keySet();
            for (String key : set) {
                String value = param.get(key);
                formBody.add(key,value);
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call,  Response response) {

                try {
                    final String string = response.body().string();
                    App.baseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myCallBack.successful(string);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call call, final IOException e) {
                final String string=e.toString();
                App.baseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.failure(string);
                    }
                });

            }
        });
    }

    @Override
    public void Filed(String url, String fileName, File file, Map<String, String> map, MyCallback myCallback) {

    }


    /**
     * get 请求
     * @param url
     * @param param
     * @param myCallBack
     */
    public void Get(String url, Map<String, String> param, final MyCallback myCallBack) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        if (param != null && param.size() > 0) {
            Set<String> set = param.keySet();
            StringBuffer sb = new StringBuffer();
            sb.append("?");
            int num = 0;
            int size = param.keySet().size();
            for (String key : set) {
                String value = param.get(key);
                sb.append(key);
                sb.append("=");
                sb.append(value);
                num++;
                if (num < size) {
                    sb.append("&");
                }
            }
            url = url + sb.toString();
        }
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie",sharedPreferences.getString("cookie",""))
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call,  Response response) {

                try {
                    final String string = response.body().string();
                    App.baseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myCallBack.successful(string);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call call,IOException e) {

                final String string = e.toString();
                App.baseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.failure(string);
                    }
                });


            }
        });
    }


}
