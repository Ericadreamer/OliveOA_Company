package com.oliveoa.jsonbean;

import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.Goods;
import com.oliveoa.pojo.Properties;

import java.util.ArrayList;

public class GoodInfoJsonBean {
    private int status;
    private String msg;
    private Properties data;

    public GoodInfoJsonBean(int status, String msg, Properties data) {
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

    public Properties getData() {
        return data;
    }

    public void setData(Properties  data) {
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
