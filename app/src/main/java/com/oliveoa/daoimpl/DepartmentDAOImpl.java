package com.oliveoa.daoimpl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oliveoa.dao.DepartmentDAO;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.util.DBHelper;

import java.util.ArrayList;

public class DepartmentDAOImpl implements DepartmentDAO{

    private DBHelper mHelpter = null;


    public DepartmentDAOImpl(Context context) {
        mHelpter = new DBHelper(context);
    }

    @Override
    public void insertDepartment(DepartmentInfo departmentInfo) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        db.execSQL("insert into department_info(dcid,dpid,id,name,telephone,fax) values(?,?,?,?,?,?)",
                new Object[]{departmentInfo.getDcid(),departmentInfo.getDpid(),departmentInfo.getId(),departmentInfo.getName(),
                        departmentInfo.getTelephone(),departmentInfo.getFax()});
        db.close();
    }

    @Override
    public void deleteDepartment(String dcid) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        db.execSQL("delete from department_info where dcid = ? ",
                new Object[]{dcid});
        db.close();
    }

    @Override
    public void updateDepartment(DepartmentInfo departmentInfo) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        db.execSQL("update department_info set dcid = ?,dpid = ?,id = ?,name = ? ,telephone = ? ,fax = ? where dcid = ?",
                new Object[]{departmentInfo.getDcid(),departmentInfo.getDpid(),departmentInfo.getId(),departmentInfo.getName(),
                        departmentInfo.getTelephone(),departmentInfo.getFax(),departmentInfo.getDcid()});
        db.close();
    }

    @Override
    public ArrayList<DepartmentInfo> getDepartments() {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        ArrayList<DepartmentInfo> list = new ArrayList<DepartmentInfo>();
        Cursor c = db.rawQuery("select * from department_info",null);
        int num=0;
        while (c.moveToNext()) {
            num++;
            DepartmentInfo department = new DepartmentInfo();
            department.setDcid(c.getString(c.getColumnIndex("dcid")));
            department.setDpid(c.getString(c.getColumnIndex("dpid")));
            department.setId(c.getString(c.getColumnIndex("id")));
            department.setName(c.getString(c.getColumnIndex("name")));
            department.setTelephone(c.getString(c.getColumnIndex("telephone")));
            department.setFax(c.getString(c.getColumnIndex("fax")));
            list.add(department);
        }
        Log.i("num=", String.valueOf(num));
        c.close();
        db.close();

        return list;
    }

    @Override
    public boolean isExists(String id) {
        SQLiteDatabase db = mHelpter.getWritableDatabase();
        ArrayList<DepartmentInfo> list = new ArrayList<DepartmentInfo>();
        Cursor c = db.rawQuery("select * from department_info where id = ? ", new String[]{id});
        boolean isExists = c.moveToNext();
        c.close();
        db.close();
        return isExists;
    }
}