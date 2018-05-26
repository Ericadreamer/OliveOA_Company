package com.oliveoa.jsonbean;

public class UpdateCompanyInfoJsonBean {
    private int status;
    private String msg;

    public UpdateCompanyInfoJsonBean(int status, String msg) {
        this.status = status;
        this.msg = msg;
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

    @Override
    public String toString() {
        return "UpdateCompanyInfoJsonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
