package com.oliveoa.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "oliveoa_company.db";
    private static final int version = 1;
   // 创建员工表、部门表、职务表、物品表，属性列为：id（主键并且自动增加）、名称+属性（）；
    private static final String SQL_CREATESTAFF = "create table employee_info(_id integer primary key autoincrement," +
                                             "eid text,dcid text,pcid text,dname text,pname text,id text,name text," +
                                             "sex text,birth text,tel text,email text,address text)";

    private static final String SQL_CREATEDEPARTMENT = "create table department_info(_id integer primary key autoincrement," +
            "dcid text,dpid text,id text,name text,telephone text,fax text)";

    private static final String SQL_CREATEDUTY = "create table duty_info(_id integer primary key autoincrement," +
            "pcid text,ppid text,name text,dcid text,mlimit text)";

    private static final String SQL_CREATEPROPERTIES = "create table properties_info(_id integer primary key autoincrement," +
            "gid text,name text,describe text,total text,remaining text,pcid text)";



    private static final String SQL_DROPSTAFF = "drop table if exists employee_info";
    private static final String SQL_DROPDEPARTEMNT = "drop table if exists department_info";
    private static final String SQL_DROPDUTY = "drop table if exists duty_info";
    private static final String SQL_DROPPROPERTIES = "drop table if exists properties_info";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATEDEPARTMENT);
        db.execSQL(SQL_CREATEDUTY);
        db.execSQL(SQL_CREATESTAFF);
        db.execSQL(SQL_CREATEPROPERTIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROPDEPARTEMNT);
        db.execSQL(SQL_CREATEDEPARTMENT);
        db.execSQL(SQL_DROPDUTY);
        db.execSQL(SQL_CREATEDUTY);
        db.execSQL(SQL_DROPSTAFF);
        db.execSQL(SQL_CREATESTAFF);
        db.execSQL(SQL_DROPPROPERTIES);
        db.execSQL(SQL_CREATEPROPERTIES);
    }

}