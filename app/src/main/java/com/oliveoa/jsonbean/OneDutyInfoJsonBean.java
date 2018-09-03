package com.oliveoa.jsonbean;

import com.oliveoa.pojo.DutyInfo;

import java.util.ArrayList;

public class OneDutyInfoJsonBean {
    private int status;
    private DutyInfo data;

    public OneDutyInfoJsonBean(){

    }

    public OneDutyInfoJsonBean(int status, DutyInfo data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DutyInfo getData() {
        return data;
    }

    public void setData(DutyInfo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OneDutyInfoJsonBean{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
