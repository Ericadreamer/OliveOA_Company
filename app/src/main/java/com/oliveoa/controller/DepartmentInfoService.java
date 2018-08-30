package com.oliveoa.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.jsonbean.CompanyLoginJsonBean;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.OneDepartmentInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
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

    //更新部门信息
    public UpdateDepartmentInfoJsonBean updatedepartmentinfo (String s, DepartmentInfo department) {
        try {
            Log.i("info_Login","知道了session："+s);
            Log.d("updateinfo",department.toString());
            FormBody body = new FormBody.Builder()
                    .add("dcid",department.getDcid())
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
                    .url(Const.UPDATE_DEPARTMENT_INFO)
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

    //检查部门id是否已经存在
    public UpdateDepartmentInfoJsonBean checkdepartmentid (String s, DepartmentInfo department) {
        try {
            Log.i("info_Login","知道了session："+s);
            Log.d("updateinfo",department.toString());
            FormBody body = new FormBody.Builder()
                    .add("id",department.getId())
                    .build();
            Log.d("updateinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.CHECK_DEPARTMENT_ID)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);

            java.lang.reflect.Type type = new TypeToken<UpdateDepartmentInfoJsonBean>() {
            }.getType();
            UpdateDepartmentInfoJsonBean updateDepartmentInfoJsonBean = gson.fromJson(json, type);
            System.out.println("checkdepartmentid = " + updateDepartmentInfoJsonBean);

            return updateDepartmentInfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;

    }

    //添加部门
    public UpdateDepartmentInfoJsonBean adddepartment (String s, DepartmentInfo department) {
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
                    .url(Const.ADD_DEPARTMENT)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);

            java.lang.reflect.Type type = new TypeToken<UpdateDepartmentInfoJsonBean>() {
            }.getType();
            UpdateDepartmentInfoJsonBean updateDepartmentInfoJsonBean = gson.fromJson(json, type);
            System.out.println("adddepartment = " + updateDepartmentInfoJsonBean);

            return updateDepartmentInfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;

    }

    //删除部门
    public StatusAndMsgJsonBean deletedepartment (String s, String dcid) {
        try {
            Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .add("dcid",dcid)
                    .build();
            Log.d("deleteinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DEPARTMENT_DELETE)
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

    //获取单个部门信息
    public OneDepartmentInfoJsonBean getdepartmentinfo (String s,String dcid) {

        try {
            Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .add("dcid",dcid)
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.GET_DEPARTMENT_INFO)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());


            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);
            java.lang.reflect.Type type = new TypeToken<OneDepartmentInfoJsonBean>() {
            }.getType();
            OneDepartmentInfoJsonBean departmentinfoJsonBean = gson.fromJson(json, type);
            System.out.println("getdepartmentinfo = " + departmentinfoJsonBean);

            return departmentinfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //查找平级部门
    public DepartmentInfoJsonBean paralleldepartment (String s) {

        try {
            Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.CHECK_CHILDREN_PARALLEL_DEPARTMENT)
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
            System.out.println("paralleldepartment = " + departmentinfoJsonBean);

            return departmentinfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

}
