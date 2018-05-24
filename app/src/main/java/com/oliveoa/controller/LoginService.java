package com.oliveoa.controller;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.HttpResponseObject;
import com.oliveoa.jsonbean.CompanyLoginJsonBean;
import com.oliveoa.jsonbean.LogoutJsonBean;
import com.oliveoa.pojo.CompanyInfo;
import com.oliveoa.view.LoginActivity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginService {
   /* public static void main(String args[]){
       // login("123","1234");
        //logout();
    }*/

    private String s;
    public HttpResponseObject<CompanyInfo> login( String username, String password) {
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

           //Headers headers = response.headers();
            //Log.i("info_respons.headers",headers+"");

            //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
            Headers headers = response.headers();
            Log.d("info_headers", "header " + headers);
            List<String> cookies = headers.values("Set-Cookie");
            String session = cookies.get(0);
            Log.d("info_cookies", "onResponse-size: " + cookies);

            s = session.substring(0, session.indexOf(";"));
            Log.i("info_s", "session is  :" + s);

            //json解析
            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<CompanyLoginJsonBean>() {
            }.getType();
            CompanyLoginJsonBean companyLoginJsonBean = gson.fromJson(json, type);
            //CompanyLoginJsonBean companyLoginJsonBean = new CompanyLoginJsonBean(-1,"nothing",null);
            System.out.println("companyLoginJsonBean = " + companyLoginJsonBean);

            HttpResponseObject<CompanyInfo> httpResponseObject = new HttpResponseObject<>(companyLoginJsonBean.getStatus(), companyLoginJsonBean.getMsg(), companyLoginJsonBean.getData(),s);
            return httpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    public LogoutJsonBean logout(String s) {

        try {

            //Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.COMPANY_LOGOUT)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            //Headers headers = response.headers();
            //Log.i("info_respons.headers",headers+"");

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);
            java.lang.reflect.Type type = new TypeToken<LogoutJsonBean>() {
            }.getType();
            LogoutJsonBean logoutJsonBean = gson.fromJson(json, type);
            System.out.println("companyLogoutJsonBean = " + logoutJsonBean.getStatus());

             return logoutJsonBean;




        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

}





