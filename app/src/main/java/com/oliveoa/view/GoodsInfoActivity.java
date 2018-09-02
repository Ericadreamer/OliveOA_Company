package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.GoodInfoService;
import com.oliveoa.jsonbean.GoodInfoJsonBean;
import com.oliveoa.pojo.PropertiesInfo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GoodsInfoActivity extends AppCompatActivity {

    private ImageView back,edit;
    private TextView tname,tquantity,tdescription,tduty;

    private String TAG = this.getClass().getSimpleName();
    private PropertiesInfo propertiesInfo;
    private String dname;
    private String pname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);

        propertiesInfo = getIntent().getParcelableExtra("pp");
        dname = getIntent().getStringExtra("dname");
        pname = getIntent().getStringExtra("panme");
        Log.e(TAG,propertiesInfo.toString());
        Log.e(TAG,"dname="+dname+"pname="+pname);
        initView();
    }

    public void initView() {
        back = (ImageView)findViewById(R.id.back);
        edit = (ImageView)findViewById(R.id.info_edit);
        tname = (TextView)findViewById(R.id.goods_name);
        tquantity = (TextView)findViewById(R.id.goods_quantity);
        tdescription = (TextView)findViewById(R.id.goods_description);
        tduty = (TextView)findViewById(R.id.show);

        initData();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                back();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Log.e(TAG,"pp=="+propertiesInfo.toString());
                Intent intent = new Intent(GoodsInfoActivity.this, EditGoodsActivity.class);
                intent.putExtra("pp",propertiesInfo);
                intent.putExtra("dname",dname);
                intent.putExtra("pname",pname);
                intent.putExtra("index",0);
                startActivity(intent);
                finish();
            }
        });


    }
    public void initData(){
         tname.setText(propertiesInfo.getName());
         tdescription.setText(propertiesInfo.getDescribe());
         tquantity.setText(String.format("%s/%s", propertiesInfo.getRemaining(), propertiesInfo.getTotal()));
         tduty.setText("");
    }

    private void back() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        Handler mHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                GoodInfoService goodInfoService = new GoodInfoService();
                GoodInfoJsonBean goodInfoJsonBean = goodInfoService.properties(s);
                if(goodInfoJsonBean.getStatus()==0){
                    ArrayList<PropertiesInfo> propertiesInfos = goodInfoJsonBean.getData();

                    Intent intent = new Intent(GoodsInfoActivity.this, GoodsActivity.class);
                    intent.putExtra("allpp",propertiesInfos);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),goodInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                }


                Log.d(TAG,"uiThread2------"+Thread.currentThread());//子线程
            }
        };

        Log.d(TAG,"uiThread1------"+Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            return true;
            //调用双击退出函数
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 双击退出函数
     */
    private static Boolean isESC = false;

    private void exitBy2Click() {
        Timer tExit ;
        if (!isESC) {
            isESC = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isESC = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            System.exit(0);
        }
    }
}
