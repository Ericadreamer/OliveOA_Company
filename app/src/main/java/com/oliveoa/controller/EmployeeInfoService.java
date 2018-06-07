package com.oliveoa.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.jsonbean.EmployeeInfoJsonBean;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EmployeeInfoService {

    public EmployeeInfoJsonBean employeeinfo (String s,String dcid) {

        try {
            Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .add("dcid",dcid)
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.EMPLOYEE_INFO_GETBYDEPARTMENT)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());


            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);
            java.lang.reflect.Type type = new TypeToken<EmployeeInfoJsonBean>() {
            }.getType();
            EmployeeInfoJsonBean employeeInfoJsonBean = gson.fromJson(json, type);
            System.out.println("employeeInfoJsonBean = " + employeeInfoJsonBean);

            return employeeInfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }
}
