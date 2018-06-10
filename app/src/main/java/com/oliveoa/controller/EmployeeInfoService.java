package com.oliveoa.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.jsonbean.EmployeeInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.EmployeeInfo;

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


    //更新员工信息
    public StatusAndMsgJsonBean updateemployeeinfo (String s, EmployeeInfo employee) {
        try {
            Log.i("info_Login","知道了session："+s);
            Log.d("updateinfo",employee.toString());
            FormBody body = new FormBody.Builder()
                    .add("eid",employee.getEid())
                    .add("dcid",employee.getDcid())
                    .add("pcid",employee.getPcid())
                    .add("id",employee.getId())
                    .add("name",employee.getName())
                    .add("sex",employee.getSex())
                    .add("birth",employee.getBirth())
                    .add("tel",employee.getTel())
                    .add("email",employee.getEmail())
                    .add("address",employee.getAddress())
                    .build();
            Log.d("updateinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.EMPLOYEE_INFO_UPDATE)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);

            java.lang.reflect.Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean updateGoodInfoJsonBean = gson.fromJson(json, type);
            System.out.println("updateGoodInfoJsonBean"+updateGoodInfoJsonBean.toString());

            return updateGoodInfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;

    }

    //添加员工
    public StatusAndMsgJsonBean addemployee (String s, EmployeeInfo employee) {
        try {
            Log.i("info_Login","知道了session："+s);
            Log.d("addinfo",employee.toString());
            FormBody body = new FormBody.Builder()
                    .add("eid",employee.getEid())
                    .add("dcid",employee.getDcid())
                    .add("pcid",employee.getPcid())
                    .add("id",employee.getId())
                    .add("name",employee.getName())
                    .add("sex",employee.getSex())
                    .add("birth",employee.getBirth())
                    .add("tel",employee.getTel())
                    .add("email",employee.getEmail())
                    .add("address",employee.getAddress())
                    .build();
            Log.d("addinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.EMPLOYEE_INFO_ADD)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);

            java.lang.reflect.Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean addGoodInfoJsonBean = gson.fromJson(json, type);
            System.out.println("addemployee = " + addGoodInfoJsonBean);

            return addGoodInfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;

    }
    //删除员工
    public StatusAndMsgJsonBean deleteemployee (String s, String eid) {
        try {
            Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .add("eid",eid)
                    .build();
            Log.d("deleteinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.EMPLOYEE_INFO_DELETE)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);

            java.lang.reflect.Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean statusAndMsgJsonBean = gson.fromJson(json, type);
            System.out.println("StatusAndMsgJsonBean = " + statusAndMsgJsonBean);

            return statusAndMsgJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;

    }

}
