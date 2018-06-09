package com.oliveoa.daoimpl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oliveoa.dao.EmployeeDAO;
import com.oliveoa.pojo.EmployeeInfo;
import com.oliveoa.util.DBHelper;

import java.util.ArrayList;

/**
 * 数据访问接口实现
 */
public class EmployeeDAOImpl implements EmployeeDAO{

        private DBHelper mHelpter = null;

        public EmployeeDAOImpl(Context context) {
            mHelpter = new DBHelper(context);
        }

        @Override
        public void insertEmployee(EmployeeInfo employeeInfo) {
            SQLiteDatabase db = mHelpter.getWritableDatabase();
            db.execSQL("insert into employee_info(eid,dcid,pcid,id,name,sex,birth,tel,email,address) values(?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{employeeInfo.getEid(),employeeInfo.getDcid(),employeeInfo.getPcid(),employeeInfo.getId(),
                                 employeeInfo.getName(),employeeInfo.getSex(),employeeInfo.getBirth(),employeeInfo.getTel(),
                                 employeeInfo.getEmail(),employeeInfo.getAddress()});
            db.close();
        }

        @Override
        public void deleteEmployee(String eid) {
            SQLiteDatabase db = mHelpter.getWritableDatabase();
            db.execSQL("delete from employee_info where eid = ? ",
                    new Object[]{eid});
            db.close();
        }

        @Override
        public void updateEmployee(EmployeeInfo employeeInfo) {
            SQLiteDatabase db = mHelpter.getWritableDatabase();
            db.execSQL("update employee_info set dcid = ?,pcid = ?,id = ?,name = ? ,sex = ?,birth =?, tel =? ,email =?,address=? where id = ?",
                    new Object[]{employeeInfo.getEid(),employeeInfo.getDcid(),employeeInfo.getPcid(),employeeInfo.getId(),
                                 employeeInfo.getName(),employeeInfo.getSex(),employeeInfo.getBirth(),employeeInfo.getTel(),
                                 employeeInfo.getEmail(),employeeInfo.getAddress(),employeeInfo.getId()});
            db.close();
        }

        @Override
        public ArrayList<EmployeeInfo> getEmployees(String dcid) {
            Log.i("DCID=",dcid);
            SQLiteDatabase db = mHelpter.getWritableDatabase();
            ArrayList<EmployeeInfo> list = new ArrayList<EmployeeInfo>();
            Cursor c = db.rawQuery("select * from employee_info where dcid = ?", new String[]{dcid});
            int num=0;
            while (c.moveToNext()) {
                num++;
                EmployeeInfo employee = new EmployeeInfo();
                  employee.setEid(c.getString(c.getColumnIndex("eid")));
                  employee.setDcid(c.getString(c.getColumnIndex("dcid")));
                  employee.setPcid(c.getString(c.getColumnIndex("pcid")));
                  employee.setId(c.getString(c.getColumnIndex("id")));
                  employee.setName(c.getString(c.getColumnIndex("name")));
                  employee.setSex(c.getString(c.getColumnIndex("sex")));
                  employee.setBirth(c.getString(c.getColumnIndex("birth")));
                  employee.setTel(c.getString(c.getColumnIndex("tel")));
                  employee.setEmail(c.getString(c.getColumnIndex("email")));
                  employee.setAddress(c.getString(c.getColumnIndex("address")));
                list.add(employee);
            }
            Log.i("num=", String.valueOf(num));
            c.close();
            db.close();

            return list;
        }
        @Override
        public EmployeeInfo getEmployee(String id) {
            SQLiteDatabase db = mHelpter.getWritableDatabase();
            EmployeeInfo employee = new EmployeeInfo();
            Cursor c = db.rawQuery("select * from employee_info where id = ?", new String[]{id});
            while (c.moveToNext()) {
                employee.setEid(c.getString(c.getColumnIndex("eid")));
                employee.setDcid(c.getString(c.getColumnIndex("dcid")));
                employee.setPcid(c.getString(c.getColumnIndex("pcid")));
                employee.setId(c.getString(c.getColumnIndex("id")));
                employee.setName(c.getString(c.getColumnIndex("name")));
                employee.setSex(c.getString(c.getColumnIndex("sex")));
                employee.setBirth(c.getString(c.getColumnIndex("birth")));
                employee.setTel(c.getString(c.getColumnIndex("tel")));
                employee.setEmail(c.getString(c.getColumnIndex("email")));
                employee.setAddress(c.getString(c.getColumnIndex("address")));
            }
            c.close();
            db.close();
            return employee;
        }

        @Override
        public boolean isExists(String id) {
            SQLiteDatabase db = mHelpter.getWritableDatabase();
            ArrayList<EmployeeInfo> list = new ArrayList<EmployeeInfo>();
            Cursor c = db.rawQuery("select * from employee_info where id = ? ", new String[]{id});
            boolean isExists = c.moveToNext();
            c.close();
            db.close();
            return isExists;
        }
    }
