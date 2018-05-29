package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
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
import com.oliveoa.jsonbean.UpdateCompanyInfoJsonBean;
import com.oliveoa.jsonbean.UpdateDepartmentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;

import java.util.Timer;
import java.util.TimerTask;


import static com.oliveoa.util.Validator.isFixPhone;
import static com.oliveoa.util.Validator.isMobile;


public class CreateDepartmentActivity extends AppCompatActivity {

    private DepartmentInfo departmentInfo;
    private EditText tid,tname,ttelephone,tfax,tdpid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_department);


        ImageView back = (ImageView)findViewById(R.id.null_back);
        ImageView save = (ImageView)findViewById(R.id.info_save);


        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateDepartmentActivity.this, DepartmentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {   //点击保存键，提示保存是否成功
            @Override
            public void onClick(View view) {
                save();
            }
        });

    }

    private void initView() {
        if(departmentInfo != null){
            tid = (EditText)findViewById(R.id.edit_num);
            tid.setText(departmentInfo.getId());
            tname = (EditText)findViewById(R.id.edit_name);
            tname.setText(departmentInfo.getName());
            ttelephone = (EditText)findViewById(R.id.edit_tel);
            ttelephone.setText(departmentInfo.getTelephone());
            tfax = (EditText)findViewById(R.id.edit_fax);
            tfax.setText(departmentInfo.getFax());
            tdpid = (EditText)findViewById(R.id.edit_superior);
            tdpid.setText(departmentInfo.getDpid());
        }
    }

    public void save() {
        departmentInfo.setId(tid.getText().toString().trim());
        departmentInfo.setName(tname.getText().toString().trim());
        departmentInfo.setTelephone(ttelephone.getText().toString().trim());
        departmentInfo.setFax(tfax.getText().toString().trim());
        departmentInfo.setDpid(tdpid.getText().toString().trim());

        if (TextUtils.isEmpty(departmentInfo.getId())||TextUtils.isEmpty(departmentInfo.getName())||TextUtils.isEmpty(departmentInfo.getTelephone())||TextUtils.isEmpty(departmentInfo.getFax())||TextUtils.isEmpty(departmentInfo.getDpid())) {
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        } else if(!isMobile(departmentInfo.getTelephone())){
            Toast.makeText(getApplicationContext(), "公司电话格式输入错误！请以手机格式重新输入", Toast.LENGTH_SHORT).show();
        } else if(!isFixPhone(departmentInfo.getFax())){
            Toast.makeText(getApplicationContext(), "传真机格式输入错误！请以固话格式重新输入", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                    UpdateDepartmentInfoJsonBean updateDepartmentInfoJsonBean = departmentInfoService.updatedepartmentinfo(s,departmentInfo);
                    Log.d("update" ,updateDepartmentInfoJsonBean.getMsg()+"");

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
