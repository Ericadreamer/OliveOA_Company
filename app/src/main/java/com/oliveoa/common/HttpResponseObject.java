package com.oliveoa.common;

public class HttpResponseObject<T> {
    private int status;
    private String msg;
    private T data;
    private String cookies;

    public HttpResponseObject(){
        super();
    }

    public HttpResponseObject(int status,String msg,T data,String cookies){
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.cookies = cookies;
    }


    public int getStatus() {
        return status;
    }

    public String getCookies() {
        return cookies;
    }

    @Override
    public String toString() {
        return "HttpResponseObject{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", cookies='" + cookies + '\'' +
                '}';
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

   /* public String getMsg() {
        return msg;

    }*/

    public T getData() {
        return data;
    }



}
