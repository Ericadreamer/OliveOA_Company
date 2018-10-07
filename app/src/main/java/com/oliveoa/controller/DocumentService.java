package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.jsonbean.OfficialDocumentInfoJsonBean;
import com.oliveoa.pojo.OfficialDocument;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DocumentService {
    /**
     *  获取公文详情（）
     */
    public StatusAndMsgAndDataHttpResponseObject<OfficialDocumentInfoJsonBean> getdocumentInfo(String s ,String aid){

        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("odid",aid)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_GETDETAILS)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<OfficialDocumentInfoJsonBean>>() {
            }.getType();
            StatusAndMsgAndDataHttpResponseObject<OfficialDocumentInfoJsonBean> statusAndMsgAndDataHttpResponseObject  = gson.fromJson(json, type);

            System.out.println(" StatusAndMsgAndDataHttpResponseObject<OfficialDocumentInfoJsonBean> = " +  statusAndMsgAndDataHttpResponseObject);

            return statusAndMsgAndDataHttpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  获取全部公文（）
     */
    public StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocumentInfoJsonBean>> getalldocument(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_GETALL)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocumentInfoJsonBean>>>() {
            }.getType();
            StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocumentInfoJsonBean>> statusAndMsgAndDataHttpResponseObject  = gson.fromJson(json, type);

            System.out.println(" StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocumentInfoJsonBean>> = " +  statusAndMsgAndDataHttpResponseObject);

            return statusAndMsgAndDataHttpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }
}
