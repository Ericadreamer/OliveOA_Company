package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DutyInfoActivity extends AppCompatActivity {

    private DutyInfo dutyInfo;
    private String dtname,dpname;
    private String TAG = this.getClass().getSimpleName();
    private TextView tname,tnum,tppid;
    private DutyInfoDao dutyInfoDao;
    private DepartmentInfoDao departmentInfoDao;
    private DepartmentInfo departmentInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutyinfo);
        dtname = getIntent().getStringExtra("dtname");//上级职务
        dutyInfo = getIntent().getParcelableExtra("dt");
        dpname = getIntent().getStringExtra("dpname");//职务所属部门的上级部门
        initData();

    }

    //初始化
    public  void initData(){
       departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
       departmentInfo = departmentInfoDao.queryBuilder().unique();
       initView();
    }

    public void initView() {
        ImageView back = (ImageView)findViewById(R.id.null_back);
        ImageView edit = (ImageView) findViewById(R.id.info_edit);
        //TextView add = (TextView)findViewById(R.id.duty_add);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                back();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });


        tname = (TextView) findViewById(R.id.tv_duty_name);
        tname.setText(dutyInfo.getName());
        tnum = (TextView) findViewById(R.id.tv_num);
        tnum.setText(dutyInfo.getLimit());
        tppid = (TextView) findViewById(R.id.text_superior);
        tppid.setText(dtname);

    }

    //返回部门信息页面
    public void back(){
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        Handler mHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");
                DutyInfoService dutyInfoService = new DutyInfoService();
                DutyInfoJsonBean dutyInfoJsonBean=dutyInfoService.dutyInfo(s,departmentInfo.getDcid());
                if (dutyInfoJsonBean.getStatus()==0) {
                    ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                    Intent intent = new Intent(DutyInfoActivity.this, DepartmentInfoActivity.class);
                    intent.putExtra("dp",departmentInfo);
                    intent.putExtra("dpname",dpname);
                    intent.putParcelableArrayListExtra("alldt",dutyInfos);
                    startActivity(intent);
                    finish();
                }

                Log.d(TAG,"uiThread2------"+Thread.currentThread());//子线程
            }
        };

        Log.d(TAG,"uiThread1------"+Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);

    }


    //编辑操作
    public void edit() {
        Intent intent = new Intent(DutyInfoActivity.this, EditDutyInfoActivity.class);
        intent.putExtra("dtname",dtname);
        intent.putExtra("dpname",dpname);
        intent.putExtra("index",0);
        intent.putExtra("dt",dutyInfo);
        startActivity(intent);
        finish();

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
