package com.jiyun.txl.kaiyuanchina_app.Modle.https.xStream;

import com.thoughtworks.xstream.XStream;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class XStreamDemo {
    private XStream xStream;

    private XStreamDemo() {
        xStream = null;
        xStream = new XStream();
    }

    private static XStreamDemo xStreamDemo;

    public static synchronized XStreamDemo getInstance() {
        if (xStreamDemo == null) {
            xStreamDemo = new XStreamDemo();
        }
        return xStreamDemo;
    }

    public XStreamDemo alies(String type, Class classes) {
        xStream.alias(type, classes);
        return xStreamDemo;
    }

    public Object build(String str) {
        Object object = xStream.fromXML(str);
        xStream = null;
        return object;
    }
}
