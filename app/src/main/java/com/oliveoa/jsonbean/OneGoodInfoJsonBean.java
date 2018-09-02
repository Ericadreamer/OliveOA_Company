package com.oliveoa.jsonbean;

import com.oliveoa.pojo.PropertiesInfo;

import java.util.ArrayList;

public class OneGoodInfoJsonBean {
    private int status;
    private String msg;
    private PropertiesInfo data;

    public OneGoodInfoJsonBean() {
    }

    public OneGoodInfoJsonBean(int status, String msg, PropertiesInfo data) {
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

    public PropertiesInfo getData() {
        return data;
    }

    public void setData(PropertiesInfo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OneGoodInfoJsonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
