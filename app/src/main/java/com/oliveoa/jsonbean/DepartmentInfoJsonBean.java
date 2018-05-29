package com.oliveoa.jsonbean;

import com.oliveoa.pojo.DepartmentInfo;

public class DepartmentInfoJsonBean {
    private int status;
    private String msg;
    private DepartmentInfo data;

    public DepartmentInfoJsonBean(int status, String msg, DepartmentInfo data) {
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

    public DepartmentInfo getData() {
        return data;
    }

    public void setData(DepartmentInfo data) {
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
}
