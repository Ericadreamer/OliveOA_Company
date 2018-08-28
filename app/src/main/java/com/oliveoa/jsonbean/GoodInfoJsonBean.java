package com.oliveoa.jsonbean;

import com.oliveoa.pojo.PropertiesInfo;

import java.util.ArrayList;

public class GoodInfoJsonBean {
    private int status;
    private String msg;
    private ArrayList<PropertiesInfo> data;

    public GoodInfoJsonBean(int status, String msg, ArrayList<PropertiesInfo> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public GoodInfoJsonBean() {

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

    public ArrayList<PropertiesInfo> getData() {
        return data;
    }

    public void setData(ArrayList<PropertiesInfo>  data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GoodInfoJsonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
