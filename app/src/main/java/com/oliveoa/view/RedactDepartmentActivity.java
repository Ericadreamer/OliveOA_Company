package com.oliveoa.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
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
import com.oliveoa.jsonbean.UpdateDepartmentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.util.EntityManager;

import java.util.Timer;
import java.util.TimerTask;

public class RedactDepartmentActivity extends AppCompatActivity {

    private DepartmentInfo departmentInfo;
    private String TAG = this.getClass().getSimpleName();
    private EditText tname,ttelephone,tfax;
    private TextView tdpid,tid;
    private String id;
    private int index;
    private String dpname;//上级部门
    private DepartmentInfoDao departmentInfoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redact_department);
        id = getIntent().getStringExtra("id");//dcid
        index = getIntent().getIntExtra("index",index);//1为部门选择列表，0为详情页
        initData();
    }

    //初始化
    public void initData(){
           departmentInfoDao= EntityManager.getInstance().departmentInfoDao;
           departmentInfo = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(id)).unique();
           DepartmentInfo temp = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(departmentInfo.getDpid())).unique();
           if(temp==null){
               dpname = "无";
           }else{
               dpname = temp.getName();
           }
           initView();
    }

    public void initView() {

        tid = (TextView) findViewById(R.id.edit_num);
        tid.setText(departmentInfo.getId());
        tname = (EditText) findViewById(R.id.edit_name);
        tname.setText(departmentInfo.getName());
        ttelephone = (EditText) findViewById(R.id.edit_tel);
        ttelephone.setText(departmentInfo.getTelephone());
        tfax = (EditText) findViewById(R.id.edit_fax);
        tfax.setText(departmentInfo.getFax());
        tdpid = (TextView) findViewById(R.id.edit_superior);
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
                        Intent intent = new Intent(RedactDepartmentActivity.this, DepartmentActivity.class);
                        startActivity(intent);
                        finish();
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
                if (departmentInfoDao.queryBuilder().count() > 0)
                    departmentSelect();
                else{
                    Toast.makeText(getApplicationContext(), "当前无更多部门，无法选择，请创建新部门！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dpselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (departmentInfoDao.queryBuilder().count()  > 0)
                    departmentSelect();
                else{
                    Toast.makeText(getApplicationContext(), "当前无更多部门，无法选择，请创建新部门！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //上级部门选择
    public void departmentSelect(){
        Intent intent = new Intent(RedactDepartmentActivity.this, DepartmentSelectActivity.class);
        intent.putExtra("index",0);
        startActivity(intent);
        finish();
    }

    //保存编辑
    public void save(){
        SharedPreferences pref = getSharedPreferences("department", MODE_PRIVATE);
       // departmentInfo.get(index).setId(tid.getText().toString().trim());
        departmentInfo.setName(tname.getText().toString().trim());
        departmentInfo.setTelephone(ttelephone.getText().toString().trim());
        departmentInfo.setFax(tfax.getText().toString().trim());


        Log.e("departmentInfo111",departmentInfo.toString());

        if (TextUtils.isEmpty(departmentInfo.getId())||TextUtils.isEmpty(departmentInfo.getName())||TextUtils.isEmpty(departmentInfo.getTelephone())||TextUtils.isEmpty(departmentInfo.getFax())) {
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

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
