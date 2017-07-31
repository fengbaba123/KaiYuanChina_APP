package com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss;

import android.support.annotation.Nullable;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;



public interface IRetrofit {


    @GET
    Call<ResponseBody> Get(@Nullable @Header("Cookie") String cookie, @Url String url, @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> Post(@Nullable @Header("Cookie") String cookie,@Url String url, @FieldMap Map<String, String> map);
    @Multipart
    @POST
    Call<ResponseBody> Feild(@Nullable @Header("Cookie") String cookie,@Url String url,@QueryMap Map<String,String> map,@Nullable @Part MultipartBody.Part file);
}
