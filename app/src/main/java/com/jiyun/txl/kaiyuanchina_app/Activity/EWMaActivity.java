package com.jiyun.txl.kaiyuanchina_app.Activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.ArrayList;
import java.util.Hashtable;

public class EWMaActivity extends AppCompatActivity {
    private static final int QR_WIDTH = 400;
    private static final int QR_HEIGHT = 400;
    private ArrayList<String> card = new ArrayList<>();
    ImageView ewmimg;
    TextView ewmname;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewma);

        ewmimg = (ImageView) findViewById(R.id.ewmimg);
        ewmname = (TextView) findViewById(R.id.ewmname);
        card.clear();
        card.add("冯玉苗");
        card.add("********");
        card.add("公司_冯氏国际");
        card.add("职位_CEO");
        card.add("职称_总裁");
        card.add("邮箱_1022868821.com");
        card.add("地址_北京");
        qrcode(view);
        ewmname.setText(card.toString());


    }

    public void qrcode(View view){
        ewmimg.setImageBitmap(createQrcode(enQrCodeOneContact(card)));
    }
    public Bitmap createQrcode(String qrcode) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            if (qrcode == null || "".equals(qrcode) || qrcode.length() < 1) {
                Toast.makeText(EWMaActivity.this,"数据为空",Toast.LENGTH_SHORT).show();
                return null;
            }
            // TODO 编码格式
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // TODO 二维码宽高、颜色
            BitMatrix bitMatrix = writer.encode(qrcode,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff0000ff;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }

                }
            }
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            return  bitmap;
        } catch (Exception e) {
            Log.e("All_Exception", e.toString());
            Toast.makeText(EWMaActivity.this,"数据太大，请重新选择",Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    public String enQrCodeOneContact(ArrayList<String> nameCard){

        StringBuilder ss = new StringBuilder();
        ss.append(String.format("BEGIN:VCARD\n" +
                "VERSION:3.0\n" +
                "N:%s",nameCard.get(0)))
                .append(String.format("\nTEL;iPhone;VOICE:%s",nameCard.get(1)))
                .append("\nORG:" + nameCard.get(2))
                .append("\nTITLE:"+nameCard.get(3))
                .append("\nROLE:"+nameCard.get(4))
                .append("\nEMAIL:"+nameCard.get(5))
                .append("\nADR:"+nameCard.get(6))
                .append("\nEND:VCARD");
        return ss.toString();

    }

}
