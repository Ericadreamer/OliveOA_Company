package com.oliveoa.daoimpl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oliveoa.dao.DutyDAO;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.DBHelper;

import java.util.ArrayList;

public class DutyDAOImpl implements DutyDAO{

    private DBHelper mHelpter = null;

    public DutyDAOImpl(Context context) {
        mHelpter = new DBHelper(context);
    }

    @Override
    public void insertDuty(DutyInfo dutyInfo) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        db.execSQL("insert into duty_info(pcid,ppid,name,dcid,mlimit) values(?,?,?,?,?)",
                new Object[]{dutyInfo.getPcid(),dutyInfo.getPpid(),dutyInfo.getName(),dutyInfo.getDcid(),
                        dutyInfo.getLimit()});
        db.close();
    }

    @Override
    public void deleteDuty(String pcid) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        db.execSQL("delete from duty_info where pcid = ? ",
                new Object[]{pcid});
        db.close();
    }

    @Override
    public void updateDuty(DutyInfo dutyInfo) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        db.execSQL("update duty_info set pcid = ?,ppid = ?,name = ? ,dcid = ? ,mlimit = ? where pcid = ?",
                new Object[]{dutyInfo.getPcid(),dutyInfo.getPpid(),dutyInfo.getName(),dutyInfo.getDcid(),
                        dutyInfo.getLimit(),dutyInfo.getPcid()});
        db.close();
    }

    @Override
    public ArrayList<DutyInfo> getDutys(String dcid) {
        Log.i("DCID=",dcid);
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        ArrayList<DutyInfo> list = new ArrayList<DutyInfo>();
        Cursor c = db.rawQuery("select * from duty_info where dcid = ?", new String[]{dcid});
        int num=0;
        while (c.moveToNext()) {
            num++;
            DutyInfo duty = new DutyInfo();
            duty.setPpid(c.getString(c.getColumnIndex("ppid")));
            duty.setDcid(c.getString(c.getColumnIndex("dcid")));
            duty.setPcid(c.getString(c.getColumnIndex("pcid")));
            duty.setName(c.getString(c.getColumnIndex("name")));
            duty.setLimit(Integer.parseInt(c.getString(c.getColumnIndex("mlimit"))));
            list.add(duty);
        }
        Log.i("num=", String.valueOf(num));
        c.close();
        db.close();

        return list;
    }

    @Override
    public boolean isExists(String name) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        ArrayList<DutyInfo> list = new ArrayList<DutyInfo>();
        Cursor c = db.rawQuery("select * from duty_info where id = ? ", new String[]{name});
        boolean isExists = c.moveToNext();
        c.close();
        db.close();
        return isExists;
    }
}
