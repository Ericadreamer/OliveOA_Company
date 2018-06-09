package com.oliveoa.daoimpl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oliveoa.dao.PropertyDAO;
import com.oliveoa.pojo.Properties;
import com.oliveoa.util.DBHelper;

import java.util.ArrayList;

public class PropertyDAOImpl implements PropertyDAO{

    private DBHelper mHelpter = null;

    public PropertyDAOImpl(Context context) {
        mHelpter = new DBHelper(context);
    }
    @Override
    public void insertProperty(Properties properties) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        db.execSQL("insert into properties_info(gid,name,describe,total,remaining,pcid) values(?,?,?,?,?,?)",
                new Object[]{properties.getGid(),properties.getName(),properties.getDescribe(),properties.getTotal(),
                        properties.getRemaining(),properties.getPcid()});
        db.close();
    }

    @Override
    public void deleteProperty(String gid) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        db.execSQL("delete from properties_info where gid = ? ",
                new Object[]{gid});
        db.close();
    }

    @Override
    public void updateProperty(Properties properties) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        db.execSQL("update properties_info set gid = ?,name = ?,describe = ?,total = ? ,remaining= ? ,pcid = ? where gid = ?",
                new Object[]{properties.getGid(),properties.getName(),properties.getDescribe(),properties.getTotal(),
                        properties.getRemaining(),properties.getPcid(),properties.getGid()});
        db.close();
    }

    @Override
    public ArrayList<Properties> getProperties() {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        ArrayList<Properties> list = new ArrayList<Properties>();
        Cursor c = db.rawQuery("select * from properties_info",null);
        int num=0;
        while (c.moveToNext()) {
            num++;
            Properties properties = new Properties();
            properties.setGid(c.getString(c.getColumnIndex("gid")));
            properties.setName(c.getString(c.getColumnIndex("name")));
            properties.setDescribe(c.getString(c.getColumnIndex("describe")));
            properties.setTotal(c.getString(c.getColumnIndex("total")));
            properties.setRemaining(c.getString(c.getColumnIndex("remaining")));
            properties.setPcid(c.getString(c.getColumnIndex("pcid")));
            list.add(properties);
        }
        Log.i("num=", String.valueOf(num));
        c.close();
        db.close();

        return list;
    }

    @Override
    public boolean isExists(String name) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        ArrayList<Properties> list = new ArrayList<Properties>();
        Cursor c = db.rawQuery("select * from properties_info where id = ? ", new String[]{name});
        boolean isExists = c.moveToNext();
        c.close();
        db.close();
        return isExists;
    }
}
