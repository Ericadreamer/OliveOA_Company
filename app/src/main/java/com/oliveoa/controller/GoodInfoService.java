package com.oliveoa.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.jsonbean.GoodInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.Properties;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodInfoService {

    //获取物品信息
    public GoodInfoJsonBean properties(String s) {

        try {
            Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.GOODS_SEARCH)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());


            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);
            java.lang.reflect.Type type = new TypeToken<GoodInfoJsonBean>() {
            }.getType();
            GoodInfoJsonBean goodInfoJsonBean = gson.fromJson(json, type);
            System.out.println("goodinfoJsonBean = " + goodInfoJsonBean);

            return goodInfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //更新物品信息
    public StatusAndMsgJsonBean updatepropertyinfo (String s, Properties property) {
        try {
            Log.i("info_Login","知道了session："+s);
            Log.d("updateinfo",property.toString());
            FormBody body = new FormBody.Builder()
                    .add("gid",property.getGid())
                    .add("name",property.getName())
                    .add("describe",property.getDescribe())
                    .add("total",property.getTotal())
                    .add("remaining",property.getRemaining())
                    .add("pcid",property.getPcid())
                    .build();
            Log.d("updateinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.GOODS_UPDATE)
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

    //添加物品
    public StatusAndMsgJsonBean addproperty (String s, Properties property) {
        try {
            Log.i("info_Login","知道了session："+s);
            Log.d("addinfo",property.toString());
            FormBody body = new FormBody.Builder()
                    .add("gid",property.getGid())
                    .add("name",property.getName())
                    .add("describe",property.getDescribe())
                    .add("total",property.getTotal())
                    .add("remaining",property.getRemaining())
                    .add("pcid",property.getPcid())
                    .build();
            Log.d("addinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.GOODS_ADD)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);

            java.lang.reflect.Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean addGoodInfoJsonBean = gson.fromJson(json, type);
            System.out.println("addproperty = " + addGoodInfoJsonBean);

            return addGoodInfoJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;

    }
    //删除物品
    public StatusAndMsgJsonBean deleteduty (String s, String gid) {
        try {
            Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .add("gid",gid)
                    .build();
            Log.d("deleteinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.GOODS_DELETE)
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
