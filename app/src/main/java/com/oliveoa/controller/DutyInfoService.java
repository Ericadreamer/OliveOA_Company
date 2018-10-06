package com.oliveoa.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.OneDutyInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.jsonbean.UpdateDepartmentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DutyInfoService {

    //获取职务信息
    public DutyInfoJsonBean dutyInfo(String s,String dcid) {

        try {
            Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .add("dcid",dcid)
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Const.DUTY_SEARCH)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());


            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);
            java.lang.reflect.Type type = new TypeToken<DutyInfoJsonBean>() {
            }.getType();
            DutyInfoJsonBean dutyInfoJsonBean = gson.fromJson(json, type);
            System.out.println("DutyInfoJsonBean = " + dutyInfoJsonBean);

            return dutyInfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //更新职务信息
    public StatusAndMsgJsonBean updatedutyinfo (String s, DutyInfo duty) {
        try {
            Log.i("info_Login","知道了session："+s);
            Log.d("updateinfo",duty.toString());
            FormBody body = new FormBody.Builder()
                    .add("pcid",duty.getPcid())
                    .add("ppid",duty.getPpid())
                    .add("limit", String.valueOf(duty.getLimit()))
                    .add("name",duty.getName())
                    .build();
            Log.d("updateinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DUTY_UPDATE)
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

    //添加职务
    public StatusAndMsgJsonBean addduty (String s, DutyInfo duty) {
        try {
            Log.i("info_Login","知道了session："+s);
            Log.d("addinfo",duty.toString());
            FormBody body = new FormBody.Builder()
                    .add("dcid",duty.getDcid())
                    .add("ppid",duty.getPpid())
                    .add("limit", String.valueOf(duty.getLimit()))
                    .add("name",duty.getName())
                    .build();
            Log.d("addinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DUTY_ADD)
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

    //删除职务
    public StatusAndMsgJsonBean deleteduty (String s, String pcid) {
        try {
            Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .add("pcid",pcid)
                    .build();
           // Log.d("deleteinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DUTY_DELETE)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            //System.out.println(json);

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

    //根据pcid获取指定职务信息
    public OneDutyInfoJsonBean getoneduty(String s, String pcid) {

        try {
            Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .add("pcid",pcid)
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DUTY_SEARCHBYID)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());


            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);
            java.lang.reflect.Type type = new TypeToken<OneDutyInfoJsonBean>() {
            }.getType();
            OneDutyInfoJsonBean oneDutyInoJsonBean = gson.fromJson(json, type);
            System.out.println("OneDutyInfoJsonBean = " + oneDutyInoJsonBean);

            return oneDutyInoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

}
