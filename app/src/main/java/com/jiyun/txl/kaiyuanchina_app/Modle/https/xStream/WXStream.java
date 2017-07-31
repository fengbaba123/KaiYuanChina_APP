package com.jiyun.txl.kaiyuanchina_app.Modle.https.xStream;

import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.InformetionBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.LoginBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.NewsDtilsBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.QueryBean;
import com.thoughtworks.xstream.XStream;

import static android.R.id.input;



public class WXStream {
    private XStream xStream = new XStream();

    //获取资讯
    public InformetionBean getInformetion(String str) {

        xStream.alias("oschina", InformetionBean.class);
        xStream.alias("news", InformetionBean.NewsBean.class);
        xStream.alias("newstype", InformetionBean.NewsBean.NewstypeBean.class);
        InformetionBean messageBean = (InformetionBean) xStream.fromXML(str);
        return messageBean;
    }

    //获取新闻详情
    public NewsDtilsBean getNewsDtis(String str) {
        xStream.alias("oschina", NewsDtilsBean.class);
        xStream.alias("relative", NewsDtilsBean.NewsBean.RelativeBean.class);
        NewsDtilsBean bean = (NewsDtilsBean) xStream.fromXML(str);
        return bean;

    }

    //搜索
    public QueryBean getspeach(String str) {
        xStream.alias("oschina", QueryBean.class);
        xStream.alias("result", QueryBean.ResultBean.class);
        QueryBean queryBean = (QueryBean) xStream.fromXML(str);
        return queryBean;
    }

    //登录
    public LoginBean getLogin(String str) {
        xStream.alias("oschina", LoginBean.class);
        LoginBean loginbean = (LoginBean) xStream.fromXML(str);
        return loginbean;
    }

    private WXStream() {
    }

    private static WXStream wxStream;

    public static synchronized WXStream getInstance() {
        if (wxStream == null) {
            wxStream = new WXStream();
        }
        return wxStream;
    }
}
