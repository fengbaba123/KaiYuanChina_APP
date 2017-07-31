package com.jiyun.txl.kaiyuanchina_app.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 类描述:  时间类
 */

public class Dates {

    //判断是几天前
    public static String getDate(String date) {
        String SystemDate = getDate(System.currentTimeMillis(), "yyyy-MM-dd");//得到当前年月日
        long todayLIngCheng = getMorning(new Date()).getTime(); //今天的凌晨时间
        long newSTime = getDate(date, "yyyy-MM-dd HH:mm:ss");//发布的时间
        long systemLong = new Date(System.currentTimeMillis()).getTime();//当前的时间
        long newsDate = getDate(date, "yyyy-MM-dd");//获取到发布的日期
        long D_Date = getDate(SystemDate, "yyyy-MM-dd");//当前日期
        if (newSTime != 0) {
            long poortime = systemLong - newSTime;//发布的时间距离现在的时间相差多少毫秒
            long poor_s = poortime / 1000;  //现在就是相差多少秒
            if (poor_s < 60) {
                return poor_s + "秒前";
            }
            if (poor_s < 3600) {
                return poor_s / 60 + "分钟前";
            }
            if (newSTime > todayLIngCheng) {
                return poor_s / 3600 + "小时前";
            }
            if (Integer.parseInt(getDate((D_Date - newsDate), "d")) == 1) {
                return "昨天";
            }
            if (Integer.parseInt(getDate((D_Date - newsDate), "d")) == 2) {
                return "前天";
            } else {
                return Integer.parseInt(getDate((D_Date - newsDate), "d")) + "天前";
            }
        }
        return null;
    }

    //获取今天凌晨时间
    private static Date getMorning(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    private static String getDate(long time, String geshi) {
        SimpleDateFormat sdf = new SimpleDateFormat(geshi);
        Date date = new Date(time);
        return sdf.format(date);
    }

    private static Long getDate(String strTime, String geshi) {
        SimpleDateFormat sdf = new SimpleDateFormat(geshi);
        try {
            return sdf.parse(strTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //判断是否是今天
    public static Boolean getToday(String date) {
        String SystemDate = getDate(System.currentTimeMillis(), "yyyy-MM-dd");//得到当前年月日
        if (date.startsWith(SystemDate)) {
            return true;
        } else {
            return false;
        }
    }

}
