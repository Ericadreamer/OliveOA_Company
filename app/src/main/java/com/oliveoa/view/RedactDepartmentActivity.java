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
    private EditText tid,tname,ttelephone,tfax;
    private TextView tdpid;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redact_department);

        departmentInfo = getIntent().getParcelableArrayListExtra("ParcelableDepartment");
        index = getIntent().getIntExtra("index",index);
        initView();

        ImageView back = (ImageView)findViewById(R.id.null_back);
        ImageView save = (ImageView)findViewById(R.id.info_save);


        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RedactDepartmentActivity.this, DepartmentInfoActivity.class);
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

    //初始化
    public void initView() {
        SharedPreferences pref = getSharedPreferences("department", MODE_PRIVATE);
        tid = (EditText) findViewById(R.id.edit_num);
        tid.setText(pref.getString("id[" + index + "]", ""));
        tname = (EditText) findViewById(R.id.edit_name);
        tname.setText(pref.getString("name[" + index + "]", ""));
        ttelephone = (EditText) findViewById(R.id.edit_tel);
        ttelephone.setText(pref.getString("telephone[" + index + "]", ""));
        tfax = (EditText) findViewById(R.id.edit_fax);
        tfax.setText(pref.getString("fax[" + index + "]", ""));
        tdpid = (TextView) findViewById(R.id.text_superior);
        if (departmentInfo.get(index).getDpid() == null) {
            tdpid.setText("无");
        } else {
            for (int i = 0; i < departmentInfo.size(); i++) {
                if (departmentInfo.get(index).getDpid().equals(departmentInfo.get(i).getDcid())) {
                    tdpid.setText(departmentInfo.get(i).getName());
                }
            }
        }
    }
    public void save(){
        departmentInfo.get(index).setId(tid.getText().toString().trim());
        departmentInfo.get(index).setName(tname.getText().toString().trim());
        departmentInfo.get(index).setTelephone(ttelephone.getText().toString().trim());
        departmentInfo.get(index).setFax(tfax.getText().toString().trim());
        departmentInfo.get(index).setDpid(tdpid.getText().toString().trim());

        if (TextUtils.isEmpty(departmentInfo.get(index).getId())||TextUtils.isEmpty(departmentInfo.get(index).getName())||TextUtils.isEmpty(departmentInfo.get(index).getTelephone())||TextUtils.isEmpty(departmentInfo.get(index).getFax())) {
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        } else if(!isMobile(departmentInfo.get(index).getTelephone())){
            Toast.makeText(getApplicationContext(), "公司电话格式输入错误！请以手机格式重新输入", Toast.LENGTH_SHORT).show();
        } else if(!isFixPhone(departmentInfo.get(index).getFax())){
            Toast.makeText(getApplicationContext(), "传真机格式输入错误！请以固话格式重新输入", Toast.LENGTH_SHORT).show();
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
            editor.putString("id["+index+"]",departmentInfo.get(index).getId());
            editor.putString("name["+index+"]",departmentInfo.get(index).getName());
            editor.putString("telephone["+index+"]",departmentInfo.get(index).getTelephone());
            editor.putString("fax["+index+"]",departmentInfo.get(index).getFax());
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
