package com.jiyun.txl.kaiyuanchina_app.Modle.https.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;



public class MyManager {
    private final String SHUJU_NAME = "kaiyuan.db";
    private final int SHUJU_VERSION = 1;
    private final String TABLE_NAME = "sousuoit";
    private SQLiteDatabase base;
    private Context context;
    private Helper helper;

    /**
     * uid 唯一 name唯一 cookie唯一
     *
     * @param context
     */
    public MyManager(Context context) {
        this.context = context;
        helper = new Helper(context, SHUJU_NAME, SHUJU_VERSION);
        base = helper.getWritableDatabase();
    }

    private boolean succ(Long ll) {
        boolean boo = false;
        if (ll > 0) {
            boo = true;
        } else {
            boo = false;
        }
        return boo;
    }

    public boolean insert(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sousuoname", name);
        long insert = base.insert(TABLE_NAME, null, contentValues);
        return succ(insert);
    }

    public List<String> queryAll() {
        List<String> list = new ArrayList<>();
        Cursor query = base.query(TABLE_NAME, null, null, null, null, null, null);
        while (query.moveToNext()) {
            String sousuoname = query.getString(query.getColumnIndex("sousuoname"));
            list.add(sousuoname);

        }
        return list;
    }

    public void delete() {
        base.delete(TABLE_NAME, null, null);
    }


    public String query() {
        List<String> list = new ArrayList<>();
        Cursor query = base.query(TABLE_NAME, null, null, null, null, null, null);
        while (query.moveToNext()) {
            String sousuoname = query.getString(query.getColumnIndex("sousuoname"));
            return sousuoname;
        }
        return null;
    }
}
