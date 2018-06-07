package com.oliveoa.jsonbean;

import com.oliveoa.pojo.EmployeeInfo;

import java.util.ArrayList;
import java.util.List;

public class EmployeeInfoJsonBean {
    private int status;
    private String msg;
    private ArrayList<EmployeeInfo> data;

    public EmployeeInfoJsonBean(int status, String msg, ArrayList<EmployeeInfo> data) {
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

    public ArrayList<EmployeeInfo> getData() {
        return data;
    }

    public void setData(ArrayList<EmployeeInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EmployeeInfoJsonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public EmployeeInfoJsonBean() {
        super();
    }
}
