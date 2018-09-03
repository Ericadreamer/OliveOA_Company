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
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.UpdateDepartmentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RedactDepartmentActivity extends AppCompatActivity {

    private DepartmentInfo departmentInfo;
    private String TAG = this.getClass().getSimpleName();
    private EditText tname,ttelephone,tfax;
    private TextView tdpid,tid;
    private int index;
    private String dpname;//上级部门
    private DepartmentInfoDao departmentInfoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redact_department);
        dpname = getIntent().getStringExtra("dpname");//上级部门
        index = getIntent().getIntExtra("index",index);//1为部门选择列表，0为详情页
        departmentInfo = getIntent().getParcelableExtra("dp");
        Log.e(TAG,departmentInfo.toString());
        initData();
    }

    //初始化
    public void initData(){
           departmentInfoDao= EntityManager.getInstance().departmentInfoDao;
         //如果是从部门选择页面返回
        if(index==1){
            departmentInfo = departmentInfoDao.queryBuilder().unique();
        }
        initView();
    }

    public void initView() {

        tid = (TextView) findViewById(R.id.edit_num);
        tname = (EditText) findViewById(R.id.edit_name);
        ttelephone = (EditText) findViewById(R.id.edit_tel);
        tfax = (EditText) findViewById(R.id.edit_fax);
        tdpid = (TextView) findViewById(R.id.edit_superior);

        tid.setText(departmentInfo.getId());
        tname.setText(departmentInfo.getName());
        ttelephone.setText(departmentInfo.getTelephone());
        tfax.setText(departmentInfo.getFax());
        tdpid.setText(dpname);

        ImageView back = (ImageView)findViewById(R.id.null_back);
        ImageView save = (ImageView)findViewById(R.id.info_save);
        ImageView dpselect = (ImageView)findViewById(R.id.depart_select);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RedactDepartmentActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确定退出编辑,直接返回部门列表页面？");
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

    //返回部门列表操作
    public void back(){
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
                    Intent intent = new Intent(RedactDepartmentActivity.this, DepartmentActivity.class);
                    intent.putParcelableArrayListExtra("alldp",departmentInfos);
                    startActivity(intent);
                    finish();
                }

                Log.d(TAG,"uiThread2------"+Thread.currentThread());//子线程
            }
        };

        Log.d(TAG,"uiThread1------"+Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);
    }
    //上级部门选择
    public void departmentSelect(){
        final DepartmentInfo t = new DepartmentInfo() ;
        t.setId(tid.getText().toString().trim());
        t.setName(tname.getText().toString().trim());
        t.setTelephone(ttelephone.getText().toString().trim());
        t.setFax(tfax.getText().toString().trim());
        t.setDpid(departmentInfo.getDpid());
        t.setDcid(departmentInfo.getDcid());
        t.setCreatetime(departmentInfo.getCreatetime());
        t.setOrderby(departmentInfo.getOrderby());
        t.setUpdatetime(departmentInfo.getUpdatetime());

        //Log.e(TAG,t.toString());

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
                        Intent intent = new Intent(RedactDepartmentActivity.this, DepartmentSelectActivity.class);
                        intent.putParcelableArrayListExtra("alldp", departmentInfos);
                        intent.putExtra("index", 0);//判断是编辑页面0还是添加页面1
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

        departmentInfo.setId(tid.getText().toString().trim());
        departmentInfo.setName(tname.getText().toString().trim());
        departmentInfo.setTelephone(ttelephone.getText().toString().trim());
        departmentInfo.setFax(tfax.getText().toString().trim());
        Log.e(TAG,departmentInfo.toString());

        Log.e("departmentInfo111",departmentInfo.toString());

        if (TextUtils.isEmpty(departmentInfo.getId())||TextUtils.isEmpty(departmentInfo.getName())||TextUtils.isEmpty(departmentInfo.getTelephone())||TextUtils.isEmpty(departmentInfo.getFax())) {
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    if(tdpid.getText().toString().trim().equals("无")||tdpid.getText().toString().trim().equals("")){
                        departmentInfo.setDpid("");
                    }
                    DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                    UpdateDepartmentInfoJsonBean updateDepartmentInfoJsonBean = departmentInfoService.updatedepartmentinfo(s, departmentInfo);
                    Log.d("update", updateDepartmentInfoJsonBean.getMsg() + "");

                    if (updateDepartmentInfoJsonBean.getStatus() == 0) {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "保存成功！点击返回键返回主页", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), updateDepartmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
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
