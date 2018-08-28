package com.oliveoa.jsonbean;

import android.os.Parcel;
import android.os.Parcelable;

import com.oliveoa.pojo.DepartmentInfo;

import java.util.ArrayList;

public class DepartmentInfoJsonBean{
    private int status;
    private String msg;
    private ArrayList<DepartmentInfo> data;

    public DepartmentInfoJsonBean(int status, String msg, ArrayList<DepartmentInfo> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DepartmentInfo> getData() {
        return data;
    }

    public void setData(ArrayList<DepartmentInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DepartmentInfoJsonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    //创建带参Parcel构造器
  /*  protected DepartmentInfoJsonBean(Parcel in) {
        //这里read字段的顺序要与write的顺序一致

        status = in.readInt();
        msg = in.readString();
    }*/

}
