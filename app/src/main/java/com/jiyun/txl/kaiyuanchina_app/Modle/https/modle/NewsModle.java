package com.jiyun.txl.kaiyuanchina_app.Modle.https.modle;

import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;



public interface NewsModle {
    void Zixun(String pageIndex,MyCallback myCallback);
    void blog(String pageIndex,MyCallback myCallback);
}
