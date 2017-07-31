package com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss;


public class HttpFactory {

//    public static final int OKHTTP = 1;
//    public static final int RETROFIT = 2;


    public static IHttp getFactory() {
        IHttp iuSer = Retrofitimple.getInstance();
//        switch (Inter) {
//            case OKHTTP:
//                iuSer = OkHttpUtils.getOkHttp();
//                return iuSer;
//            case RETROFIT:
//                iuSer = Retrofitimple.getInstance();
//                return iuSer;
//        }
        return iuSer;
    }
}
