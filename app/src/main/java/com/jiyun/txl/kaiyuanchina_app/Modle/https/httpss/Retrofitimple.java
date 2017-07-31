package com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class Retrofitimple implements IHttp {

    private IRetrofit iRetrofit;
    private final SharedPreferences sharedPreferences;

    private Retrofitimple() {
        sharedPreferences = App.baseActivity.getSharedPreferences("data", Context.MODE_PRIVATE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com/")
                .build();
        iRetrofit = retrofit.create(IRetrofit.class);
    }

    private static Retrofitimple retrofitimple;

    public static synchronized Retrofitimple getInstance() {
        if (retrofitimple == null) {
            retrofitimple = new Retrofitimple();
        }
        return retrofitimple;
    }


    public static void RetrofitimpleClean() {
        retrofitimple = null;
    }

    @Override
    public void Get(String url, Map<String, String> map, final MyCallback callback) {
        Call<ResponseBody> call = iRetrofit.Get(sharedPreferences.getString("cookie", ""), url, map);
        inithead(callback, call);


    }

    private void inithead(final MyCallback callback, Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        callback.successful(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        callback.failure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.failure(t.getMessage() + "");
            }
        });
    }

    @Override
    public void Post(String url, Map<String, String> map, MyCallback callback) {
        Call<ResponseBody> call = iRetrofit.Post(sharedPreferences.getString("cookie", ""), url, map);
        inithead(callback, call);
    }

    @Override
    public void Filed(String url, String fileName, File file, Map<String, String> map, MyCallback myCallback) {
        Call<ResponseBody> call;
        File file1;
        if (file == null) {
           file1 = new File("");

        } else {
           file1 = file;
        }

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        MultipartBody.Part part = MultipartBody.Part.createFormData(fileName, file1.getName(), requestFile);
        call = iRetrofit.Feild(sharedPreferences.getString("cookie", ""), url, map, part);

        inithead(myCallback, call);
    }
}
