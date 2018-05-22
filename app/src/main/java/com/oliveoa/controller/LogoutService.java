package com.oliveoa.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.HttpResponseObject;
import com.oliveoa.jsonbean.CompanyLoginJsonBean;
import com.oliveoa.pojo.CompanyInfo;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LogoutService {
      /*public static void main(String args[]){
        login("123","1234");
    }*/

    public HttpResponseObject<CompanyInfo> login(String username, String password) {
        /*
         * 1.建立http连接
         * 2.传入参数
         * 3.获取数据接口传回的json
         * 4.解析json
         * 5.获取结果
         * 6.返回true or false
         *
         */
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder().url(Const.COMPANY_LOGIN).post(body).build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            Headers headers = response.headers();
            Log.i("info_respons.headers", headers + "");

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<CompanyLoginJsonBean>() {
            }.getType();
            CompanyLoginJsonBean companyLoginJsonBean = gson.fromJson(json, type);
            //CompanyLoginJsonBean companyLoginJsonBean = new CompanyLoginJsonBean(-1,"nothing",null);
            System.out.println("companyLoginJsonBean = " + companyLoginJsonBean);
            HttpResponseObject<CompanyInfo> httpResponseObject = new HttpResponseObject<>(companyLoginJsonBean.getStatus(), companyLoginJsonBean.getMsg(), companyLoginJsonBean.getData());
            return httpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }
}
