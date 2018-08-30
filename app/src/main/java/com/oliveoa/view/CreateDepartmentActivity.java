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
import com.oliveoa.controller.CompanyInfoService;
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.UpdateCompanyInfoJsonBean;
import com.oliveoa.jsonbean.UpdateDepartmentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import static com.oliveoa.util.Validator.isFixPhone;
import static com.oliveoa.util.Validator.isMobile;


public class CreateDepartmentActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private EditText tid,tname,ttelephone,tfax;
    private TextView tdpid;
    private ImageView save,back,dpselect;
    private int index;
    private DepartmentInfo dp;//获取表中数据
    private String dpname;//上级部门
    private DepartmentInfoDao departmentInfoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_department);
        dpname = getIntent().getStringExtra("dpname");
        index =getIntent().getIntExtra("index",index);//1为部门选择列表，0为部门列表
        Log.e(TAG, String.valueOf(index)+":::"+dpname);
        initData();

    }
    private  void initData(){
        departmentInfoDao= EntityManager.getInstance().getDepartmentInfo();
        dp = departmentInfoDao.queryBuilder().unique();
        Log.e(TAG,dp.toString());
        initView();
    }

    private void initView() {

        back = (ImageView)findViewById(R.id.null_back);
        save = (ImageView)findViewById(R.id.info_save);
        dpselect = (ImageView)findViewById(R.id.depart_select);

        tid = (EditText)findViewById(R.id.edit_num);
        tname = (EditText)findViewById(R.id.edit_name);
        ttelephone = (EditText)findViewById(R.id.edit_tel);
        tfax = (EditText)findViewById(R.id.edit_fax);

        tdpid = (TextView) findViewById(R.id.edit_superior);


        if(index==1) {
            tid.setText(dp.getId());
            tname.setText(dp.getName());
            ttelephone.setText(dp.getTelephone());
            tfax.setText(dp.getFax());
            tdpid.setText(dpname);
        }

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CreateDepartmentActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确定退出创建,直接返回部门列表页面？");
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e(TAG,"点击了确定返回部门列表");
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

        tdpid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        departmentSelect();
                }
            });
        dpselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        departmentSelect();
                }
            });
  }

     public void back(){

         HandlerThread handlerThread = new HandlerThread("HandlerThread");
         handlerThread.start();

         Handler mHandler = new Handler(handlerThread.getLooper()){
             @Override
             public void handleMessage(Message msg) {
                 super.handleMessage(msg);
                 SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                 String s = pref.getString("sessionid", "");
                 DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                 DepartmentInfoJsonBean departmentInfoJsonBean = departmentInfoService.departmentInfo(s);
                 if (departmentInfoJsonBean.getStatus() == 0) {
                     ArrayList<DepartmentInfo> departmentInfos = departmentInfoJsonBean.getData();
                     Intent intent = new Intent(CreateDepartmentActivity.this, DepartmentActivity.class);
                     intent.putParcelableArrayListExtra("alldp", departmentInfos);
                     startActivity(intent);
                     finish();
                 } else {
                     Looper.prepare();
                     Toast.makeText(getApplicationContext(), departmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                     Looper.loop();
                 }
             }
     };

        Log.d(TAG,"uiThread1------"+Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);
     }
    /**
     *  跳转部门选择列表
     *  1、新建t暂存表单数据
     *  2、创建部门表并删除数据以便插入数据唯一
     *  3、线程访问获取服务器部门数据并传输到部门选择列表Activity
     */
    public void departmentSelect(){
        DepartmentInfo t = new DepartmentInfo() ;
        t.setId(tid.getText().toString().trim());
        t.setName(tname.getText().toString().trim());
        t.setTelephone(ttelephone.getText().toString().trim());
        t.setFax(tfax.getText().toString().trim());
        t.setDpid(dp.getDpid());
        Log.e(TAG,t.toString());

        departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        departmentInfoDao.deleteAll();
        departmentInfoDao.insert(t);

        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        Handler mHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");
                DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                DepartmentInfoJsonBean departmentInfoJsonBean = departmentInfoService.departmentInfo(s);
                if (departmentInfoJsonBean.getStatus()==0) {
                    ArrayList<DepartmentInfo> departmentInfos = departmentInfoJsonBean.getData();
                    if(departmentInfos.size()==0){
                        Toast.makeText(getApplicationContext(), "当前无更多部门，无法选择，请创建新部门！", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(CreateDepartmentActivity.this, DepartmentSelectActivity.class);
                        intent.putParcelableArrayListExtra("alldp", departmentInfos);
                        intent.putExtra("index", 1);//判断是编辑页面0还是添加页面1
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),departmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

                Log.d(TAG,"uiThread2------"+Thread.currentThread());//子线程
            }
        };

        Log.d(TAG,"uiThread1------"+Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);

    }
    //保存编辑
    public void save(){

        dp.setId(tid.getText().toString().trim());
        dp.setName(tname.getText().toString().trim());
        dp.setTelephone(ttelephone.getText().toString().trim());
        dp.setFax(tfax.getText().toString().trim());

        Log.e(TAG,dp.toString());
        if (TextUtils.isEmpty(dp.getId())||TextUtils.isEmpty(dp.getName())||TextUtils.isEmpty(dp.getTelephone())||TextUtils.isEmpty(dp.getFax())) {
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                    UpdateDepartmentInfoJsonBean isexist = departmentInfoService.checkdepartmentid(s,dp);
                    Log.d("checkid", isexist.getMsg() + "");

                    if(isexist.getStatus()==0) {
                        UpdateDepartmentInfoJsonBean updateDepartmentInfoJsonBean = departmentInfoService.adddepartment(s, dp);
                        Log.d("update", updateDepartmentInfoJsonBean.getMsg() + "");

                        if (updateDepartmentInfoJsonBean.getStatus() == 0) {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "创建成功！点击返回键返回部门列表", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), updateDepartmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }else{
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(),isexist.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                }
            }).start();
        }

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
