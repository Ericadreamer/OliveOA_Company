package com.oliveoa.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.jsonbean.CompanyLoginJsonBean;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.UpdateDepartmentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DepartmentInfoService {
//    public static void main(String args[]){
//
//        departmentInfo("JSESSIONID=A98AAD9C0D3504AFA021E9E402E85028");
//    }


    public DepartmentInfoJsonBean departmentInfo(String s) {

        try {
            Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.GET_DEPARTMENT)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());


            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);
            java.lang.reflect.Type type = new TypeToken<DepartmentInfoJsonBean>() {
            }.getType();
            DepartmentInfoJsonBean departmentinfoJsonBean = gson.fromJson(json, type);
            System.out.println("departmentinfoJsonBean = " + departmentinfoJsonBean);

            return departmentinfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    public UpdateDepartmentInfoJsonBean updatedepartmentinfo (String s, DepartmentInfo department) {
        try {
            Log.i("info_Login","知道了session："+s);
            Log.d("updateinfo",department.toString());
            FormBody body = new FormBody.Builder()
                    .add("id",department.getId())
                    .add("name",department.getName())
                    .add("telephone",department.getTelephone())
                    .add("fax",department.getFax())
                    .add("dpid",department.getDpid())
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

            java.lang.reflect.Type type = new TypeToken<UpdateDepartmentInfoJsonBean>() {
            }.getType();
            UpdateDepartmentInfoJsonBean updateDepartmentInfoJsonBean = gson.fromJson(json, type);
            System.out.println("updateDepartmentInfoJsonBean = " + updateDepartmentInfoJsonBean);

            return updateDepartmentInfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;

    }
}
