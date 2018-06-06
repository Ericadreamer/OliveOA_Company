package com.oliveoa.jsonbean;

import com.oliveoa.pojo.DutyInfo;

import java.util.ArrayList;

public class DutyInfoJsonBean {
    private int status;
    private String msg;
    private ArrayList<DutyInfo> data;

    public DutyInfoJsonBean(){

    }
    public DutyInfoJsonBean(int status, String msg, ArrayList<DutyInfo> data) {
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

    public ArrayList<DutyInfo> getData() {
        return data;
    }

    public void setData(ArrayList<DutyInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DutyInfoJsonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
