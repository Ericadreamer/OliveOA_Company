package com.oliveoa.common;

public class HttpResponseObject<T> {
    private int status;
    private String msg;
    private T data;

    public HttpResponseObject(){
        super();
    }

    public HttpResponseObject(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
