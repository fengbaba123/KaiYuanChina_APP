package com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss;

import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;

import java.io.File;
import java.util.Map;



public interface IHttp {
    void Get(String url, Map<String, String> map, MyCallback callback);

    void Post(String url, Map<String, String> map, MyCallback callback);
    void Filed(String url,String fileName,File file,Map<String,String> map,MyCallback myCallback);
}
