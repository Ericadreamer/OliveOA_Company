package com.oliveoa.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.jsonbean.CompanyLoginJsonBean;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CompanyInfoService{

        public CompanyLoginJsonBean companyinfo (String s) {

            try {
                Log.i("info_Login","知道了session："+s);
                FormBody body = new FormBody.Builder()
                        .build();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .addHeader("Cookie",s)
                        .url(Const.COMPANY_INFO)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                //System.out.println(response.body().string());

                //Headers headers = response.headers();
                //Log.i("info_respons.headers",headers+"");

                String json = response.body().string();
                Gson gson = new Gson();
                System.out.println(json);
                java.lang.reflect.Type type = new TypeToken<CompanyLoginJsonBean>() {
                }.getType();
                CompanyLoginJsonBean companyloginJsonBean = gson.fromJson(json, type);
                System.out.println("companyloginJsonBean = " + companyloginJsonBean);

                return companyloginJsonBean;

            } catch (IOException e) {
                //todo handler IOException
                //throw new RuntimeException(e);
                e.printStackTrace();
            }
            return null;
        }
}

