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
import com.oliveoa.jsonbean.UpdateDepartmentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.oliveoa.util.Validator.isFixPhone;
import static com.oliveoa.util.Validator.isMobile;

public class RedactDepartmentActivity extends AppCompatActivity {

    private ArrayList<DepartmentInfo> departmentInfo;
    private String TAG = this.getClass().getSimpleName();
    private EditText tname,ttelephone,tfax;
    private TextView tdpid,tid;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redact_department);

        departmentInfo = getIntent().getParcelableArrayListExtra("ParcelableDepartment");
        index = getIntent().getIntExtra("index",index);
        initView();
    }

    //初始化
    public void initView() {
        SharedPreferences pref = getSharedPreferences("department", MODE_PRIVATE);
        tid = (TextView) findViewById(R.id.edit_num);
        tid.setText(pref.getString("id[" + index + "]", ""));
        tname = (EditText) findViewById(R.id.edit_name);
        tname.setText(pref.getString("name[" + index + "]", ""));
        ttelephone = (EditText) findViewById(R.id.edit_tel);
        ttelephone.setText(pref.getString("telephone[" + index + "]", ""));
        tfax = (EditText) findViewById(R.id.edit_fax);
        tfax.setText(pref.getString("fax[" + index + "]", ""));
        tdpid = (TextView) findViewById(R.id.edit_superior);
        tdpid.setText(pref.getString("dpname[" + index + "]", ""));

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
                        intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfo);
                        //intent.putExtra("index",index);
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
                if (departmentInfo.size() > 0)
                    departmentSelect();
                else{
                    Toast.makeText(getApplicationContext(), "当前无更多部门，无法选择，请创建新部门！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dpselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (departmentInfo.size() > 0)
                    departmentSelect();
                else{
                    Toast.makeText(getApplicationContext(), "当前无更多部门，无法选择，请创建新部门！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //上级部门选择
    public void departmentSelect(){
        saveDepartmentinfo();
        Intent intent = new Intent(RedactDepartmentActivity.this, DepartmentSelectActivity.class);
        intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfo);
        intent.putExtra("index",index);
        startActivity(intent);
        finish();
    }

    //保存编辑
    public void save(){
        SharedPreferences pref = getSharedPreferences("department", MODE_PRIVATE);
       // departmentInfo.get(index).setId(tid.getText().toString().trim());
        departmentInfo.get(index).setName(tname.getText().toString().trim());
        departmentInfo.get(index).setTelephone(ttelephone.getText().toString().trim());
        departmentInfo.get(index).setFax(tfax.getText().toString().trim());
        departmentInfo.get(index).setDpid(pref.getString("dpid["+index+"]",""));

        Log.e("departmentInfo111",departmentInfo.get(index).toString());

        if (TextUtils.isEmpty(departmentInfo.get(index).getId())||TextUtils.isEmpty(departmentInfo.get(index).getName())||TextUtils.isEmpty(departmentInfo.get(index).getTelephone())||TextUtils.isEmpty(departmentInfo.get(index).getFax())) {
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                    UpdateDepartmentInfoJsonBean updateDepartmentInfoJsonBean = departmentInfoService.updatedepartmentinfo(s, departmentInfo.get(index));
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

    /**
     *  数据存储到SharedPreferences文件中
     *
     */
    public void saveDepartmentinfo(){
        SharedPreferences.Editor editor = getSharedPreferences("department",MODE_PRIVATE).edit();
        for (int i = 0;i<departmentInfo.size();i++){
            editor.putString("id["+index+"]",tid.getText().toString().trim());
            editor.putString("name["+index+"]",tname.getText().toString().trim());
            editor.putString("telephone["+index+"]",ttelephone.getText().toString().trim());
            editor.putString("fax["+index+"]",tfax.getText().toString().trim());
            editor.apply();
        }
        Log.e(TAG, "" + departmentInfo.toString());
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
