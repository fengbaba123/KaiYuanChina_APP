package com.jiyun.txl.kaiyuanchina_app.View.radio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.InputStream;


public class RadioView {
    public static final void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public static Bitmap getCircleImg(Bitmap bitmap) {
        //创建一个空的Bitmip
        Bitmap newBitmip = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        //创建一个画布
        Canvas canvas = new Canvas(newBitmip);
//创建画笔
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        //抗锯齿让他没有毛边
        paint.setAntiAlias(true);
        int radios = bitmap.getWidth() / 2 > bitmap.getHeight() / 2 ? bitmap.getHeight() / 2 : bitmap.getWidth() / 2;
        //画圆形
        canvas.drawCircle(bitmap.getWidth() / 2
                , bitmap.getHeight() / 2, radios, paint);
        //取交集 取上部分
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return newBitmip;
    }

    public static Bitmap getCircleImg(InputStream inputStream) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return getCircleImg(bitmap);
    }

}
