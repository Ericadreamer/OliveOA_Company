package com.oliveoa.view;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.util.NotificationUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.math.RoundingMode;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Request;

public class DownloadService extends Service {
    private Context mContext;

    private DownloadBinder mBinder = new DownloadBinder();

    public class DownloadBinder extends Binder {
        public void startDownload(String path, final String name, String url, final int notifyId) {
            OkHttpUtils//
                    .get()//
                    .url(url)//
                    .build()//
                    .execute(new FileCallBack(path, name)//
                    {
                        @Override
                        public void onBefore(Request request, int id) {
                            super.onBefore(request, id);
                            NotificationUtil.createProgressNotification(mContext, name, "等待下载", R.drawable.oliveoa, notifyId);
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            System.out.println("onError :" + e.getMessage());
                        }

                        @Override
                        public void onResponse(File response, int id) {
                            NotificationUtil.cancelNotification(notifyId);
                            Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                            System.out.println("onResponse :" + response.getAbsolutePath());
                            //customDialog(response.getAbsolutePath());
                            openfile(response.getAbsolutePath());
                        }

                        @Override
                        public void inProgress(float progress, long total, int id) {
                            super.inProgress(progress, total, id);
                            DecimalFormat df = new DecimalFormat("0.00");
                            df.setRoundingMode(RoundingMode.HALF_UP);
                            progress = Float.parseFloat((df.format(progress)));
                            System.out.println(progress + "==  " + total + "==" + id);

                            NotificationUtil.updateNotification(notifyId, progress * 100);

                        }
                    });

        }
    }
            @Nullable
            @Override
            public IBinder onBind (Intent intent){
                return mBinder;
            }

            @Override
            public void onCreate () {
                super.onCreate();
                mContext = this;
            }

            @Override
            public int onStartCommand (Intent intent,int flags, int startId){
                return super.onStartCommand(intent, flags, startId);
            }

        private void openfile(String path) {
            try {
                FileNameMap fileNameMap = URLConnection.getFileNameMap();
                //String path = "/mnt/test/TB_DRAWING.xls";
                File file = new File(path);
                String type = fileNameMap.getContentTypeFor(path);
                //                  解决部分三星手机无法获取到类型的问题
                if (TextUtils.isEmpty(type)) {
                    String extension = MimeTypeMap.getFileExtensionFromUrl(path);
                    type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                }
                if (TextUtils.isEmpty(type)) {
                    Toast.makeText(mContext, "无法正确读取文件类型，请尝试在文件夹中直接打开！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, type);
                mContext.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(mContext, "手机上未安装能打开该文件的程序！", Toast.LENGTH_SHORT).show();
            }
        }
 }
