package com.oliveoa.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.oliveoa.util.Validator.isNumstr;

public class EditDutyInfoActivity extends AppCompatActivity {

    private EditText tname,tlimit;
    private DutyInfo dutyInfo;
    private DepartmentInfo departmentInfo;
    private DutyInfoDao dutyInfoDao;
    private DepartmentInfoDao departmentInfoDao;
    private String TAG = this.getClass().getSimpleName();
    private int index;
    private TextView tppid;
    private ImageView dutynext;
    private String dpname,dtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_duty_info);
        dpname = getIntent().getStringExtra("dpname");
        index = getIntent().getIntExtra("index",index);//0为详情页，1为职务选择页
        dutyInfo = getIntent().getParcelableExtra("dt");
        dtname = getIntent().getStringExtra("dtname");
        initData();
    }

    //初始化
    public void initData(){
        dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();

        departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        departmentInfo = departmentInfoDao.queryBuilder().unique();

        if(index==1){
            dutyInfo = dutyInfoDao.queryBuilder().unique();
        }

        initView();
    }

    public void initView() {

        tname = (EditText) findViewById(R.id.edit_duty_name);
        tname.setText(dutyInfo.getName());
        tlimit = (EditText) findViewById(R.id.edit_num);
        tlimit.setText(String.valueOf(dutyInfo.getLimit()));


        final ImageView back = (ImageView)findViewById(R.id.null_back);
        ImageView save = (ImageView)findViewById(R.id.info_save);

        dutynext = (ImageView)findViewById(R.id.duty_select);
        tppid =(TextView)findViewById(R.id.tv_superior);
        tppid.setText(dtname);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditDutyInfoActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确定退出编辑,直接返回部门信息页面？");
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       back();
                    }
                });
                dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {   //点击保存键，提示保存是否成功
            @Override
            public void onClick(View view) {
                save();
            }
        });

        tppid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dutySelect();
            }
        });
        dutynext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dutySelect();
            }
        });
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
                    Log.e(TAG,dutyInfos.toString());
                    Intent intent = new Intent(EditDutyInfoActivity.this, DepartmentInfoActivity.class);
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

    //职务选择页面跳转
    public void dutySelect(){
        final DutyInfo t = new DutyInfo();
        t.setName(tname.getText().toString().trim());
        t.setLimit(Integer.parseInt(tlimit.getText().toString().trim()));
        t.setPcid(dutyInfo.getPcid());
        t.setPpid(dutyInfo.getPpid());
        t.setDcid(dutyInfo.getDcid());
        t.setCreatetime(dutyInfo.getCreatetime());
        t.setOrderby(dutyInfo.getOrderby());
        t.setUpdatetime(dutyInfo.getUpdatetime());

        //dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
        dutyInfoDao.deleteAll();
        dutyInfoDao.insert(t);

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
                    if(dutyInfos.size()==0){
                        Toast.makeText(getApplicationContext(), "当前无更多职务，无法选择，请创建新职务！", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(EditDutyInfoActivity.this, DutySelectActivity.class);
                        intent.putExtra("index", 0);
                        intent.putExtra("alldt", dutyInfos);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

                Log.d(TAG,"uiThread2------"+Thread.currentThread());//子线程
            }
        };

        Log.d(TAG,"uiThread1------"+Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);

    }

    //保存编辑
    public void save() {
        if (isNumstr(tlimit.getText().toString().trim())) {
            dutyInfo.setName(tname.getText().toString().trim());
            dutyInfo.setLimit(Integer.parseInt(tlimit.getText().toString().trim()));

            if (TextUtils.isEmpty(dutyInfo.getName())) {
                Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
            } else if (dutyInfo.getLimit() <= 0) {
                Toast.makeText(getApplicationContext(), "职务限制人数不得小于1人，请输入人数", Toast.LENGTH_SHORT).show();
            }  else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");
                        if(tppid.getText().toString().trim().equals("无")||tppid.getText().toString().trim().equals("")){
                            dutyInfo.setPpid("");
                        }
                        Log.e(TAG,"duty=="+dutyInfo.toString());
                        DutyInfoService dutyInfoService = new DutyInfoService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = dutyInfoService.updatedutyinfo(s, dutyInfo);
                        Log.d("update", statusAndMsgJsonBean.getMsg() + "");

                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "保存成功！点击返回键返回职务列表", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }

                    }
                }).start();
            }
        }else{
            Toast.makeText(getApplicationContext(), "请输入阿拉伯数字形式的限制人数", Toast.LENGTH_SHORT).show();
        }
    }

    //判断重名
    public boolean checkdutyname(String name) {

        DutyInfo dutyInfo =dutyInfoDao.queryBuilder().where(DutyInfoDao.Properties.Name.eq(name)).unique();
        if(dutyInfo!=null) {
            Log.e(TAG, "inputname"+name+"duty[]name"+dutyInfo.getName());
            return true;
        }

        return false;
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
