package com.oliveoa.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.jsonbean.CompanyLoginJsonBean;
import com.oliveoa.jsonbean.UpdateCompanyInfoJsonBean;
import com.oliveoa.pojo.CompanyInfo;

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

    public UpdateCompanyInfoJsonBean updatecompanyinfo (String s, CompanyInfo company) {

        try {
            Log.i("info_Login","知道了session："+s);
            Log.d("updateinfo",company.toString());
            FormBody body = new FormBody.Builder()
                    .add("fullname",company.getFullname())
                    .add("telphone",company.getTelephone())
                    .add("fax",company.getFax())
                    .add("zipcode",company.getZipcode())
                    .add("address",company.getAddress())
                    .add("website",company.getWebsite())
                    .add("email",company.getEmail())
                    .add("introduction",company.getIntroduction())
                    .build();
            Log.d("updateinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.COMPANY_INFO_UPDATE)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);

            java.lang.reflect.Type type = new TypeToken<UpdateCompanyInfoJsonBean>() {
            }.getType();
            UpdateCompanyInfoJsonBean updateCompanyInfoJsonBean = gson.fromJson(json, type);
            System.out.println("updateCompanyInfoJsonBean = " + updateCompanyInfoJsonBean);

            return updateCompanyInfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    public UpdateCompanyInfoJsonBean updatepassword (String s, String pwd) {

        try {
            Log.i("info_Login","知道了session："+s);
            Log.d("updatepwd",pwd);
            FormBody body = new FormBody.Builder()
                    .add("newPassword",pwd)
                    .build();
            Log.d("updatepwdbody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.COMPANY_INFO_PASSWORD)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);

            java.lang.reflect.Type type = new TypeToken<UpdateCompanyInfoJsonBean>() {
            }.getType();
            UpdateCompanyInfoJsonBean updateCompanyInfoJsonBean = gson.fromJson(json, type);
            System.out.println("updateCompanyInfoJsonBean = " + updateCompanyInfoJsonBean);

            return updateCompanyInfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }
}

